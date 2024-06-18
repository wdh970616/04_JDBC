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

public class DeleteEmp {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        Properties prop = new Properties();
        Scanner sc = new Scanner(System.in);
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/employee-query.xml"));
            String query = prop.getProperty("deleteEmp");
            pstmt = con.prepareStatement(query);
            System.out.print("정보를 제거하실 사원의 번호를 입력해주세요 : ");
            String empId = sc.nextLine();

            EmployeeDTO deleteEmp = new EmployeeDTO();
            deleteEmp.setEmpId(empId);
            pstmt.setString(1, deleteEmp.getEmpId());

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
            System.out.println("=== 사원 정보 삭제 성공 ===");
        } else {
            System.out.println("!!! 사원 정보 삭제 실패 !!!");
        }
    }
}
