$(document).ready(function() {
	//----------------------------------------------- shoppingList 글 게시

	// 이미지 선택시 사진 보이게 하기
	$('#formFile-shopping-list').on('change', function() {
		setImageFromFile(this, '.shopping-img-place');
	});
	function setImageFromFile(input, expression) {
		if (input.files && input.files[0]) {
			let reader = new FileReader();
			reader.onload = function(e) {
				$(expression).attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}

	// 구매일 데이트피커 설정
	$('#shopping-date-datepicker').datepicker({
		dateFormat: "yy-mm-dd" // 2022-11-08
		, dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
		, showButtonPanel: true // 오늘 버튼 활성화
		, currentText: "오늘"
	});

	// 반품가능일 데이트피커 설정
	$('#shopping-returnn-datepicker').datepicker({
		dateFormat: "yy-mm-dd" // 2022-11-08
		, dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
		, showButtonPanel: true // 오늘 버튼 활성화
		, currentText: "오늘"
	});

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

		if (purchasedCategory == 'online') {
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

	// 업로드버튼 눌렀을 때
	$('#shoppingList-upload-btn').on('click', function() {
		let itemName = $('#shopping-itemName').val().trim();
		let category = $('#shopping-category option:selected').val();
		let amount = $('#shopping-amount').val().trim(); //string
		let purchasedCategory = $('#shopping-purchasedCategory option:selected').val();

		let purchased = '';
		if (purchasedCategory == 'online') {
			purchased = $('#shopping-purchased').val(); // 온라인의 경우 주소
		} else {
			purchased = document.getElementById('shopping-offline-purchased').innerHTML; // 오프라인일 경우 주소
		}

		let size = $('#shopping-size').val().trim();
		let color = $('#shopping-color').val().trim();
		let datePurchased = $('#shopping-date-datepicker').val(); //2023-02-01종류string
		let returnableDeadline = $('#shopping-returnn-datepicker').val(); //2023-02-01종류string
		let usedHope = $('#shopping-usedHope option:selected').val();
		let file = $('#formFile-shopping-list').val(); //C:\fakepath\ASCII.png

		if (file != '') {
			let ext = file.split(".").pop().toLowerCase();
			if (ext != 'jpg' && ext != 'jpeg' && ext != 'gif' && ext != 'png') {
				alert("이미지 파일만 게시할 수 있습니다.");
				$('#file').val('');
				$('#fileName').text('');
				return;
			}
		}

		if (file == '') {
			alert("파일을 선택해주세요.");
			return;
		}
		if (itemName == '') {
			alert("제품명을 입력해주세요.");
			return;
		}
		if (category == '') {
			alert("카테고리를 선택해주세요.");
			return;
		}
		if (amount == '') {
			alert("금액을 입력해주세요.");
			return;
		}
		if (purchasedCategory == '') {
			alert("구매처를 선택해주세요.");
			return;
		}
		if (purchased == '') {
			alert("구매처를 입력해주세요.");
			return;
		}
		if (datePurchased == '') {
			alert("구매일을 선택해주세요.");
			return;
		}
		if (usedHope == '') {
			alert("당근 희망여부를 선택해주세요.");
			return;
		}

		let formData = new FormData();
		formData.append("itemName", itemName);
		formData.append("category", category);
		formData.append("amount", amount);
		formData.append("purchasedCategory", purchasedCategory);
		formData.append("purchased", purchased);
		formData.append("size", size);
		formData.append("color", color);
		formData.append("datePurchased", datePurchased);
		formData.append("returnableDeadline", returnableDeadline);
		formData.append("usedHope", usedHope);
		formData.append("file", $('#formFile-shopping-list')[0].files[0]);

		$.ajax({
			// request
			type: "POST"
			, url: "/product/shopping_list_write"
			, data: formData
			, enctype: "multipart/form-data"
			, processData: false
			, contentType: false

			// response
			, success: function(data) {
				if (data.code == 1) {
					alert("업로드 되었습니다.");
					location.href = "/product/shopping_list_view";
				} else {
					alert("업로드에 실패하였습니다.");
				}
			}
			, error: function(jqXHR, textStatus, errorThrown) {
				let errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}

		});
	});//-> 업로드 버튼 끝


	//---------------------------------------------------------------->지도 api


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

	$('#keyword-map-search').on('click', function(e) {
		let keyword = $('#keyword-map').val();
		keywordSearch(keyword);
	});
});