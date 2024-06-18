package com.ohgiraffers.methods;

import com.ohgiraffers.model.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class ThisEmp {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        EmployeeDTO selectedEmp = null;
        Properties prop = new Properties();
        Scanner sc = new Scanner(System.in);
        System.out.print("조회하려는 사원의 사원번호를 입력하세요 : ");
        int emp_no = sc.nextInt();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/employee-query.xml"));
            String query = prop.getProperty("thisEmp");
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, emp_no);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                selectedEmp = new EmployeeDTO();
                selectedEmp.setEmpId(rset.getString("EMP_ID"));
                selectedEmp.setEmpName(rset.getString("EMP_NAME"));
                selectedEmp.setEmpNo(rset.getString("EMP_NO"));
                selectedEmp.setEmail(rset.getString("EMAIL"));
                selectedEmp.setPhone(rset.getString("PHONE"));
                selectedEmp.setDeptCode(rset.getString("DEPT_CODE"));
                selectedEmp.setJobCode(rset.getString("JOB_CODE"));
                selectedEmp.setSalLevel(rset.getString("SAL_LEVEL"));
                selectedEmp.setSalary(rset.getDouble("SALARY"));
                selectedEmp.setBonus(rset.getDouble("BONUS"));
                selectedEmp.setManagerId(rset.getString("MANAGER_ID"));
                selectedEmp.setHireDate(rset.getDate("HIRE_DATE"));
                selectedEmp.setEntDate(rset.getDate("ENT_DATE"));
                selectedEmp.setEntYn(rset.getString("ENT_YN"));
            }
            System.out.println(selectedEmp.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }
}
