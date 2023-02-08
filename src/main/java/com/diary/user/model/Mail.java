package com.diary.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mail {
	private String title;
	private String address;
	private String message;
}
