package com.ktds.jmj;

import java.util.InputMismatchException;

public class TheatreProgram {
	
	private int choice;
	private TheatreManage manage;
	
	
	public TheatreProgram() {
		this.manage = new TheatreManage();
	}
	
	public int getChoice() {
		return choice;
	}

	public void setChoice() {
		while(true){
			try{
				this.choice = manage.inputScannerInt();
				break;
			}
			catch(InputMismatchException ime){
				System.out.println("1또는 2를 입력해주세요.");
			}
		}
	}

	public void startProgram() {
		manage.createSeats();
		while(true) {
			System.out.println("=================");
			System.out.println("1.예약");
			System.out.println("2.종료");
			System.out.println("=================");
			
			this.setChoice();
			choice = this.getChoice();
			
			if ( choice == 1){
				manage.reservationSeats();
			}
			else if ( choice == 2){
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			}
			else {
				System.out.println("1또는 2를 입력해주세요.");
			}
		}
		
	}

}
