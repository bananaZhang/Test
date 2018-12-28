package jdbc;

import designPattern.statePattern.State;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author ZJY
 * @ClassName: JdbcTest
 * @Description: JdbcTest
 * @date 2018/12/28 14:56
 */
public class JdbcTest {

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://39.105.8.99/ris", "root", "Zjy12345+");
            Statement statement = conn.createStatement();

            String sql = "select * from memory_table where id = 3";
            ResultSet rs = statement.executeQuery(sql);

//            while (rs.next()) {
//                String name = rs.getString("name");
//                int age = rs.getInt("age");
//
//                System.out.println("name = " + name + ", age = " + age);
//            }
            rs.close();
            conn.close();
//            prepareData(13000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void prepareData(int num) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://39.105.8.99/ris", "root", "Zjy12345+");
        PreparedStatement pstmt = conn.prepareStatement("insert into dw_doctor_task ( patient_id, study_iuid, patient_sex, from_org_no, to_org_no, " +
                "create_time, update_time ) values( ?, ?, ?, ?, ?, ?, ? )");

        Random random = new Random();

        for (int i = 0; i < num; i ++) {
            String patientId = "patient" + random.nextInt(10000);
            String studyUid = "studyUid" + random.nextInt(10000);
            String patientSex = "M";
            String orgNo = "sr_test_0001_001";
            Date createTime = getRandomDate("2018-11-11", "2018-11-13");
            Date updateTime = createTime;

            pstmt.setString(1, patientId);
            pstmt.setString(2, studyUid);
            pstmt.setString(3, patientSex);
            pstmt.setString(4, orgNo);
            pstmt.setString(5, orgNo);
            pstmt.setTimestamp(6, new java.sql.Timestamp(createTime.getTime()));
            pstmt.setTimestamp(7, new java.sql.Timestamp(updateTime.getTime()));

            pstmt.execute();
        }

        pstmt.close();
        conn.close();
    }

    private static Date getRandomDate(String beginDate, String endDate) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse(beginDate);
        Date end = format.parse(endDate);
        if (start.getTime() >= end.getTime()) {
            return null;
        }
        long date = random(start.getTime(), end.getTime());
        return new Date(date);
    }

    private static long random(long begin, long end) {
        long rtn = (long) (begin + Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }
}
