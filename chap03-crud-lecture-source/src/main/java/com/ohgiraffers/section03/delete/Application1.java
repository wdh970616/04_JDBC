package com.ohgiraffers.section03.delete;

import com.ohgiraffers.model.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplats.close;
import static com.ohgiraffers.common.JDBCTemplats.getConnection;

public class Application1 {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        Properties prop = new Properties();

        Scanner sc = new Scanner(System.in);
        System.out.print("삭제를 원하시는 메뉴의 번호를 입력하세요 : ");
        int menuCode = sc.nextInt();

        MenuDTO deleteMenu = new MenuDTO();
        deleteMenu.setMenuCode(menuCode);

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("deleteMenu");
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, deleteMenu.getMenuCode());
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
            System.out.println("메뉴 삭제 성공!!!");
        } else {
            System.out.println("메뉴 삭제 실패!!!");
        }
    }
}
