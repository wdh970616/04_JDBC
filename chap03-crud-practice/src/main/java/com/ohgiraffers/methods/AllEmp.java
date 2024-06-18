package com.ohgiraffers.methods;

import com.ohgiraffers.model.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class AllEmp {
    public static void main(String[] args) {

        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;
        List<EmployeeDTO> empList = null;
        Properties prop = new Properties();
        EmployeeDTO row = new EmployeeDTO();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/employee-query.xml"));
            String query = prop.getProperty("allEmp");
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
            close(con);
        }
        for (EmployeeDTO emp : empList) {
            System.out.println(emp);
        }
    }
}
