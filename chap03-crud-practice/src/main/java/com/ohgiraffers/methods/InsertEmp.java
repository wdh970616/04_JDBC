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

public class InsertEmp {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        Properties prop = new Properties();
        Scanner sc = new Scanner(System.in);

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/employee-query.xml"));
            String query = prop.getProperty("insertEmp");
            pstmt = con.prepareStatement(query);
            System.out.print("등록하실 사원의 번호를 입력해주세요 : ");
            String empId = sc.nextLine();
            System.out.print("등록하실 사원의 이름을 입력해주세요 : ");
            String empName = sc.nextLine();
            System.out.print("등록하실 사원의 주민등록번호 앞 6자리를 입력해주세요 : ");
            String empProntNo = sc.nextLine();
            System.out.print("등록하실 사원의 주민등록번호 뒤 7자리를 입력해주세요 : ");
            String empBackNo = sc.nextLine();
            String empNo = empProntNo + "-" + empBackNo;
            System.out.print("등록하실 사원의 이메일 주소를 입력해주세요 : ");
            String email = sc.nextLine();
            System.out.print("등록하실 사원의 전화번호를 '-'없이 번호만 입력해주세요 : ");
            String phone = sc.nextLine();
            System.out.print("등록하실 사원의 부서코드를 입력해주세요 : D");
            String deptNo = sc.nextLine();
            String deptCode = "D" + deptNo;
            System.out.print("등록하실 사원의 직급코드를 입력해주세요 : J");
            String jobNo = sc.nextLine();
            String jobCode = "J" + jobNo;
            System.out.print("등록하실 사원의 급여등급을 입력해주세요 : S");
            String salNo = sc.nextLine();
            String salLevel = "S" + salNo;
            System.out.print("등록하실 사원의 급여를 입력해주세요 : ");
            int salary = sc.nextInt();
            System.out.print("등록하실 사원의 관리자사번을 입력해주세요 : ");
            String managerId = sc.nextLine();
            System.out.print("등록하실 사원의 입사연도를 입력해주세요 : ");
            int year = sc.nextInt();
            System.out.print("등록하실 사원의 입사월을 입력해주세요 : ");
            int month = sc.nextInt();
            System.out.print("등록하실 사원의 입사일을 입력해주세요 : ");
            int day = sc.nextInt();
            LocalDate javaDate = LocalDate.of(year, month, day);
            Date hireDate = Date.valueOf(javaDate);

            EmployeeDTO newEmp = new EmployeeDTO();
            newEmp.setEmpId(empId);
            newEmp.setEmpName(empName);
            newEmp.setEmpNo(empNo);
            newEmp.setEmail(email);
            newEmp.setPhone(phone);
            newEmp.setDeptCode(deptCode);
            newEmp.setJobCode(jobCode);
            newEmp.setSalLevel(salLevel);
            newEmp.setSalary(salary);
            newEmp.setManagerId(managerId);
            newEmp.setHireDate(hireDate);

            pstmt.setString(1, newEmp.getEmpId());
            pstmt.setString(2, newEmp.getEmpName());
            pstmt.setString(3, newEmp.getEmpNo());
            pstmt.setString(4, newEmp.getEmail());
            pstmt.setString(5, newEmp.getPhone());
            pstmt.setString(6, newEmp.getDeptCode());
            pstmt.setString(7, newEmp.getJobCode());
            pstmt.setString(8, newEmp.getSalLevel());
            pstmt.setDouble(8, newEmp.getSalary());
            pstmt.setString(9, newEmp.getManagerId());
            pstmt.setDate(10, newEmp.getHireDate());

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
