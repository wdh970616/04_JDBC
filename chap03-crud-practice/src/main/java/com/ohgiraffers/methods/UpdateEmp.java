package com.ohgiraffers.methods;

import com.ohgiraffers.model.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class UpdateEmp {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        Properties prop = new Properties();
        Scanner sc = new Scanner(System.in);
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/employee-query.xml"));
            String query = prop.getProperty("updateEmp");
            pstmt = con.prepareStatement(query);
            System.out.print("정보를 변경하실 사원의 번호를 입력해주세요 : ");
            String empId = sc.nextLine();
            System.out.print("새로 등록하실 사원명을 입력해주세요 : ");
            String empName = sc.nextLine();
            System.out.print("새로 등록하실 이메일 주소를 입력해주세요 : ");
            String email = sc.nextLine();
            System.out.print("새로 등록하실 전화번호를 '-'없이 번호만 입력해주세요 : ");
            String phone = sc.nextLine();

            EmployeeDTO changeEmp = new EmployeeDTO();
            changeEmp.setEmpId(empId);
            changeEmp.setEmpName(empName);
            changeEmp.setEmail(email);
            changeEmp.setPhone(phone);

            pstmt.setString(1, changeEmp.getEmpName());
            pstmt.setString(2, changeEmp.getEmail());
            pstmt.setString(3, changeEmp.getPhone());
            pstmt.setString(4, changeEmp.getEmpId());

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
            System.out.println("=== 사원 정보 등록 성공 ===");
        } else {
            System.out.println("!!! 사원 정보 등록 실패 !!!");
        }
    }
}
