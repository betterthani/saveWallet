package com.diary.user.bo;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.diary.common.EncryptUtils;
import com.diary.user.dao.UserMapperRepository;
import com.diary.user.dao.UserRepository;
import com.diary.user.model.Mail;
import com.diary.user.model.UserEntity;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailBO {
	
	// 임시비밀번호 생성,발송내용,보내기
	UserRepository userRepository;
	UserMapperRepository userMapperRepository;
	
	
	private JavaMailSender mailSender;
	private static final String fromAddress = "107106107@naver.com";
	
	// 보낼 내용
	public Mail createMailChangePassword(String email) {
		String str = getTempPassword();
		Mail maildto = new Mail();
		maildto.setAddress(email);
		maildto.setTitle("SAVE WALLET 임시비밀번호 안내 이메일입니다.");
		maildto.setMessage("안녕하세요. SAVE WALLET 임시비밀번호 안내 관련 이메일 입니다. 회원님의 임시 비밀번호는 "
				+ str + " 입니다. 로그인 후에 비밀번호를 변경을 해주세요");
		updatePassword(str,email);
		return maildto;
	}
	
	// 이메일로 발송된 임시비밀번호로 해당 유저의 패스워드 변경
	public void updatePassword(String str,String email){
		String tempPassword = EncryptUtils.md5(str);
		int id = userRepository.findByEmail(email).getId();
		userMapperRepository.updatePassword(id, tempPassword);
	}
	
	// 10자리의 랜덤난수를 생성하는 메소드
	public String getTempPassword(){
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		
		String str = "";
		
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}
	
	// 메일 보내기
	public void mailSend(Mail mail) {
		SimpleMailMessage message = new SimpleMailMessage();
		 message.setTo(mail.getAddress());
	        message.setFrom(MailBO.fromAddress);
	        message.setSubject(mail.getTitle());
	        message.setText(mail.getMessage());

	        mailSender.send(message);
	}
	
	
	
	
	
}
