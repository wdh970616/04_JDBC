package com.ohgiraffers.section02.preparedstatement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application5 {
    public static void main(String[] args) {

        // 연결객체 만들기
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        // 조회할 employee 이름의 성을 받아서 찾기
        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 사원명의 성을 입력하세요 : ");
        String empName = sc.nextLine();
        // concat(?, '%') : ?로 시작하는것
//        String query = "select * from employee where emp_name like concat(?, '%')";
        EmployeeDTO row = null;
        List<EmployeeDTO> empList = null;
        Properties prop = new Properties();
        try {
            // properties를 xml을 통해 불러옴
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/section02/preparedstatement/employee-query.xml"));
            String query = prop.getProperty("selectEmpByFamilyName");   // xml 파일의 키값이 가지고있는 밸류값을 쿼리로 사용
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, empName);
            rset = pstmt.executeQuery();
            empList = new ArrayList<>();
            while (rset.next()) {
                row = new EmployeeDTO();
                row.setEmpId(rset.getString("EMP_ID"));
                row.setEmpName(rset.getString("EMP_NAME"));
                row.setEmpNo(rset.getString("EMP_NO"));
                row.setEmail(rset.getString("EMAIL"));
                row.setPhone(rset.getString("PHONE"));
                row.setDeptCode(rset.getString("DEPT_CODE"));
                row.setJobCode(rset.getString("JOB_CODE"));
                row.setSalLevel(rset.getString("SAL_LEVEL"));
                row.setSalary(rset.getDouble("SALARY"));
                row.setBonus(rset.getDouble("BONUS"));
                row.setManagerId(rset.getString("MANAGER_ID"));
                row.setHireDate(rset.getDate("HIRE_DATE"));
                row.setEntDate(rset.getDate("ENT_DATE"));
                row.setEntYn(rset.getString("ENT_YN"));
                empList.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidPropertiesFormatException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        for (EmployeeDTO emp : empList) {
            System.out.println(emp);
        }
    }
}
