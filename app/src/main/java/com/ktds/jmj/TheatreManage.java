package com.ktds.jmj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TheatreManage {

	private static Map<Integer, List<String>> seats;
	private Scanner input;
	private List<String> seat;
	
	public TheatreManage(){
		this.seats = new HashMap<Integer, List<String>>();
		this.input = new Scanner(System.in);
	}

	public Map<Integer, List<String>> getSeats() {
		return seats;
	}

	public void setSeats(Map<Integer, List<String>> seats) {
		this.seats = seats;
	}
	
	/** 
	 * Scanner함수
	 * @return
	 */
	public String inputScanner() {
		input = new Scanner(System.in);
		return input.nextLine();
	}
	
	public int inputScannerInt(){
		input = new Scanner(System.in);
		return input.nextInt();
	}
	
	/**
	 * 초기 좌석 생성
	 * @return
	 */
	public Map<Integer, List<String>> createSeats() {
		for ( int j = 0; j < 5; j++){
			this.seat = new ArrayList<String>();
			for( int i = 0; i < 10; i++ ) {
				seat.add("□");
			}
			seats.put(j,seat);
		}
		return seats;
	}
	
	/**
	 * 좌석 출력
	 */
	public void printSeats() {
		for ( int i = 0; i < 5; i++ ){
			this.seat = this.seats.get(i);
			for ( String s : seat ) {
				System.out.print(s);
			}
			System.out.println("");
		}
	}
	
	/**
	 * 예약
	 */
	public void reservationSeats() {
		//seatNum 뽑아내기
		System.out.println("예약할 좌석을 (x,y)형식으로 입력하세요.(x번째 줄 y번째 자리)");
		printSeats();
		System.out.println("10칸씩 5줄의 좌석있다.");
		
		String seatNum = inputScanner();
				
		seatNum = seatNum.trim();
		
		seatNum = seatNum.replace(" ", ""); 
		
		String seatNumbers[] = seatNum.split(","); 
		
		int row = Integer.parseInt(seatNumbers[0]);
		int col = Integer.parseInt(seatNumbers[1]);
		
		// 존재하지 않는 좌석을 입력했다면 return으로 함수빠져나가기?
		if ( checkRowCol(row) || checkRowCol(col)){
			System.out.println("존재하지 않는 좌석입니다.");
			return;
		}
		
		 //좌석이 이미 예약 됬는지 체크
		for (int i = 0; i < 5; i++) {
			List<String> checkList = getSeats().get(row-1);
			for ( String s : checkList ){
				if (checkList.get(col-1).equals("■") ){
					System.out.println("이미 예약된 자리입니다.");
					printSeats();
					return; // 되었다면 return으로 함수빠져나가기?
				}
			}
		}
		
		// 안된좌석이라면 예약 
		List<String> selectedSeats = getSeats().get(row-1);
		selectedSeats.set(col-1, "■");
		
		System.out.println(row + "번째 줄 " + col + "번째 자리가 예약되었습니다.");
		printSeats();
		
	}
	
	// 범위안에 입력받은 좌석 숫자가 맞나
	public boolean checkRowCol (int num){
		if ( num <= 0 || num >= 10){
			return true;
		}
		else {
			return false;
		}
	}

}
