package com.ohgiraffers.methods;

import com.ohgiraffers.model.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class RetireEmp {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        Properties prop = new Properties();
        Scanner sc = new Scanner(System.in);
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/employee-query.xml"));
            String query = prop.getProperty("retireEmp");
            pstmt = con.prepareStatement(query);
            System.out.print("퇴사 처리할 사원의 번호를 입력해주세요 : ");
            String empId = sc.nextLine();
            System.out.print("퇴사 처리할 사원의 퇴사연도를 입력해주세요 : ");
            int year = sc.nextInt();
            System.out.print("퇴사 처리할 사원의 퇴사월을 입력해주세요 : ");
            int month = sc.nextInt();
            System.out.print("퇴사 처리할 사원의 퇴사일을 입력해주세요 : ");
            int day = sc.nextInt();
            LocalDate javaDate = LocalDate.of(year, month, day);
            Date entDate = Date.valueOf(javaDate);

            EmployeeDTO changeEmp = new EmployeeDTO();
            changeEmp.setEntDate(entDate);
            changeEmp.setEmpId(empId);

            pstmt.setDate(1, changeEmp.getEntDate());
            pstmt.setString(2, changeEmp.getEmpId());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }
        if (result > 0) {
            System.out.println("=== 사원 퇴사처리 성공 ===");
        } else {
            System.out.println("!!! 사원 퇴사처리 실패 !!!");
        }
    }
}
