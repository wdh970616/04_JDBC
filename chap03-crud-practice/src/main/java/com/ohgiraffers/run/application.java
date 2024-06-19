package com.ohgiraffers.run;

import com.ohgiraffers.methods.AllEmp;

import java.util.Scanner;

public class application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("""
                === 사원 정보 관리 프로그램 ===
                1. 전체 사원 조회
                2. 사번으로 사원 조회
                3. 새로운 사원 등록
                4. 기존 사원 정보 변경
                5. 기존 사원 정보 삭제
                6. 퇴사 처리
                0. 프로그램 종료
                원하시는 기능의 번호를 입력하세요 : """);
        int choice = sc.nextInt();
        do {
            switch (choice) {
                case 1:
                    new AllEmp().all();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
            }
        } while (choice != 0);
    }
}
