$(document).ready(function() {
	//---------------------------------------------------------------->지도 api

	// 구매처 온라인일 경우 적을 구매처오픈
	$('#shopping-purchasedCategory').on('change', function() {
		let purchasedCategory = $('#shopping-purchasedCategory option:selected').val();

		// 초기화
		$('#shopping-purchased').addClass('d-none');
		$('#shopping-offline-purchased').addClass('d-none');
		$('#kakaMap').addClass('d-none');

		if ($('#kakaMap').css("display") == "none") {
			window.setTimeout(function() {
				map.relayout();
			}, 0);
		}

		if (purchasedCategory == 'ONLINE') {
			$('#shopping-purchased').removeClass('d-none');
		} else {

			$('#shopping-offline-purchased').removeClass('d-none');
			$('#kakaMap').removeClass('d-none');

			if ($('#kakaMap').css("display") != "none") {
				window.setTimeout(function() {
					map.relayout();
				}, 0);
			}
		}
	});// ->구매처 온라인일 경우 적을 구매처오픈 end


	let mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
			level: 3 // 지도의 확대 레벨
		};

	// 지도를 생성합니다    
	let map = new kakao.maps.Map(mapContainer, mapOption);

	// d-none 일경우 대비 
	// 지도를 표시하는 div 크기를 변경하는 함수입니다
	function resizeMap() {
		var mapContainer = document.getElementById('map');
		mapContainer.style.width = '650px';
		mapContainer.style.height = '650px';
	}

	function relayout() {

		// 지도를 표시하는 div 크기를 변경한 이후 지도가 정상적으로 표출되지 않을 수도 있습니다
		// 크기를 변경한 이후에는 반드시  map.relayout 함수를 호출해야 합니다 
		// window의 resize 이벤트에 의한 크기변경은 map.relayout 함수가 자동으로 호출됩니다
		map.relayout();
	}

	// 장소 검색 객체를 생성합니다
	let ps = new kakao.maps.services.Places();

	// 검색 버튼 클릭시
	function keywordSearch() {

		let keyword = $('#keyword-map').val();
		let markers = [];

		if (!keyword.replace(/^\s+|\s+$/g, '')) {
			alert('키워드를 입력해주세요!');
			return;
		}

		// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
		let infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

		// 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
		ps.keywordSearch(keyword, placesSearchCB);

		// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
		function placesSearchCB(data, status, pagination) {
			if (status === kakao.maps.services.Status.OK) {

				// 정상적으로 검색이 완료됐으면
				// 검색 목록과 마커를 표출합니다
				displayPlacesOnSidebar(data);

				// 페이지 번호를 표출합니다
				displayPagination(pagination);

			} else if (status === kakao.maps.services.Status.ZERO_RESULT) {

				alert('검색 결과가 존재하지 않습니다.');
				return;

			} else if (status === kakao.maps.services.Status.ERROR) {

				alert('검색 결과 중 오류가 발생했습니다.');
				return;

			}
		}

		// 사이드바에 결과 출력 + 마커 생성
		function displayPlacesOnSidebar(places) {

			let listEl = document.getElementById('placesList'),
				menuEl = document.getElementsByClassName('menu_wrap'),
				fragment = document.createDocumentFragment(),
				bounds = new kakao.maps.LatLngBounds(),
				listStr = '';

			// 검색 결과 목록에 추가된 항목들을 제거합니다
			removeAllChildNods(listEl);

			// 지도에 표시되고 있는 마커를 제거합니다
			removeMarker();

			for (let i = 0; i < places.length; i++) {

				// 마커를 생성하고 지도에 표시합니다
				let placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
					marker = addMarker(placePosition, i),
					itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

				// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
				// LatLngBounds 객체에 좌표를 추가합니다
				bounds.extend(placePosition);

				// 마커와 검색결과 항목에 mouseover 했을때
				// 해당 장소에 인포윈도우에 장소명을 표시합니다
				// mouseout 했을 때는 인포윈도우를 닫습니다
				(function(marker, title) {
					kakao.maps.event.addListener(marker, 'mouseover', function() {
						displayInfowindow(marker, title);
					});

					kakao.maps.event.addListener(marker, 'mouseout', function() {
						infowindow.close();
					});

					itemEl.onmouseover = function() {
						displayInfowindow(marker, title);
					};

					itemEl.onmouseout = function() {
						infowindow.close();
					};

					// 마커에 클릭 이벤트를 등록한다 (우클릭 : rightclick)
					kakao.maps.event.addListener(marker, 'click', function() {

						let selectTitle = title;
						$.ajax({
							url: "/product/shopping_list_write_view"
							, data: { "purchased": selectTitle }
							, success: function(data) {
								document.getElementById("shopping-offline-purchased").innerHTML = selectTitle;
							}
							, error: function(jqXHR, textStatus, errorThrown) {
								let errorMsg = jqXHR.responseJSON.status;
								alert(errorMsg + ":" + textStatus);
							}
						})

					});

				})(marker, places[i].place_name);

				fragment.appendChild(itemEl);

			}

			// 검색결과 항목들을 검색결과 목록 Element에 추가합니다
			listEl.appendChild(fragment);
			menuEl.scrollTop = 0;

			// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			map.setBounds(bounds);
		}


		// 검색결과 항목을 Element로 반환하는 함수입니다
		function getListItem(index, places) {

			let el = document.createElement('li'),
				itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
					'<div class="info">' +
					'   <h5>' + places.place_name + '</h5>';

			if (places.road_address_name) {
				itemStr += '    <span>' + places.road_address_name + '</span>' +
					'   <span class="jibun gray">' + places.address_name + '</span>';
			} else {
				itemStr += '    <span>' + places.address_name + '</span>';
			}

			itemStr += '  <span class="tel">' + places.phone + '</span>' +
				'</div>';

			el.innerHTML = itemStr;
			el.className = 'item';

			return el;
		}

		// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
		function addMarker(position, idx, title) {
			let imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
				imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
				imgOptions = {
					spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
					spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
					offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
				},
				markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
				marker = new kakao.maps.Marker({
					position: position, // 마커의 위치
					image: markerImage
				});

			marker.setMap(map); // 지도 위에 마커를 표출합니다
			markers.push(marker);  // 배열에 생성된 마커를 추가합니다

			return marker;
		}

		// 지도 위에 표시되고 있는 마커를 모두 제거합니다
		function removeMarker() {
			for (let i = 0; i < markers.length; i++) {
				markers[i].setMap(null);
			}
			markers = [];
		}



		// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
		function displayPagination(pagination) {
			let paginationEl = document.getElementById('pagination'),
				fragment = document.createDocumentFragment(),
				i;

			// 기존에 추가된 페이지번호를 삭제합니다
			while (paginationEl.hasChildNodes()) {
				paginationEl.removeChild(paginationEl.lastChild);
			}

			for (i = 1; i <= pagination.last; i++) {
				let el = document.createElement('a');
				el.href = "#";
				el.innerHTML = i;

				if (i === pagination.current) {
					el.className = 'on';
				} else {
					el.onclick = (function(i) {
						return function() {
							pagination.gotoPage(i);
						}
					})(i);
				}

				fragment.appendChild(el);
			}
			paginationEl.appendChild(fragment);
		}

		// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
		// 인포윈도우에 장소명을 표시합니다
		function displayInfowindow(marker, title) {
			let content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

			infowindow.setContent(content);
			infowindow.open(map, marker);


		}

		// 검색결과 목록의 자식 Element를 제거하는 함수입니다
		function removeAllChildNods(el) {
			while (el.hasChildNodes()) {
				el.removeChild(el.lastChild);
			}
		}

	}
	// 글게시 화면에서 오프라인일경우 지도 검색 누를 때
	$('#keyword-map-search').on('click', function(e) {
		let keyword = $('#keyword-map').val();
		keywordSearch(keyword);
	});
	//---------------------------------------------------------------------- shoppingList Detail 수정, 확대 부분
	
	// db에서 불러온 셀렉값에 따라 변동(online, offline) -> 고정값이기에 on이벤트 생략
	let purchasedCategory = $('#purchasedCategory-detail option:selected').val();

	// 초기화
	$('#shopping-detail-purchased').addClass('d-none');
	$('#shopping-offline-detail-purchased').addClass('d-none');
	$('#kakaMap-detail').addClass('d-none');

	/// 온라인일 경우
	if ($('#kakaMap-detail').css("display") == "none") {
		window.setTimeout(function() {
			map.relayout();
		}, 0);
	}

	if (purchasedCategory == 'ONLINE') {
		$('#shopping-detail-purchased').removeClass('d-none');
	} else {

		$('#shopping-offline-detail-purchased').removeClass('d-none');
		$('#kakaMap-detail').removeClass('d-none');

		if ($('#kakaMap-detail').css("display") != "none") {
			window.setTimeout(function() {
				map.relayout();
			}, 0);
		}
	}

	// 글수정 화면에서 오프라인일경우 지도 검색 누를 때
	$('#keyword-detail-map-search').on('click', function(e) {
		let keyword = $('#keyword-map').val();
		keywordSearch(keyword);
	});

	// 자동입력 버튼(오프라인일 경우)
	setTimeout(function() {
		$('#keyword-detail-map-search').trigger('click');
	});
	//---------------------------------------------------------------------- wishList Detail 수정, 확대 부분
	// db에서 불러온 셀렉값에 따라 변동(online, offline) -> 고정값이기에 on이벤트 생략
	let wishpurchasedCategory = $('#purchasedCategory-detail-wish option:selected').val();

	// 초기화
	$('#wish-detail-purchased').addClass('d-none');
	$('#wish-offline-detail-purchased').addClass('d-none');
	$('#kakaMap-wishdetail').addClass('d-none');

	/// 온라인일 경우
	if ($('#kakaMap-wishdetail').css("display") == "none") {
		window.setTimeout(function() {
			map.relayout();
		}, 0);
	}

	if (wishpurchasedCategory == 'ONLINE') {
		$('#wish-detail-purchased').removeClass('d-none');
	} else {

		$('#wish-offline-detail-purchased').removeClass('d-none');
		$('#kakaMap-wishdetail').removeClass('d-none');

		if ($('#kakaMap-wishdetail').css("display") != "none") {
			window.setTimeout(function() {
				map.relayout();
			}, 0);
		}
	}

	// 글수정 화면에서 오프라인일경우 지도 검색 누를 때
	$('#keyword-detail-wish-search').on('click', function(e) {
		let keyword = $('#keyword-map').val();
		keywordSearch(keyword);
	});

	// 자동입력 버튼(오프라인일 경우)
	setTimeout(function() {
		$('#keyword-detail-wish-search').trigger('click');
	});



});//-> document end