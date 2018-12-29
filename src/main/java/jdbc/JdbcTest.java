package jdbc;

import designPattern.statePattern.State;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZJY
 * @ClassName: JdbcTest
 * @Description: JdbcTest
 * @date 2018/12/28 14:56
 */
public class JdbcTest {
    // 十个线程的线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    // 准备数据的数量
    private static final int prepareCount = 60000;
    // 累加器
    private volatile int count = 0;

    public static void main(String[] args) throws Exception {
        long start = new Date().getTime();
        String memorySql = "SELECT\n" +
                "\t id taskId, task_status, modality, patient_name, accession_no,\n" +
                "\t patient_id, patient_sex, allot_time, ai_tag, study_iuid,\n" +
                "\t reject_tag, reject_date, report_doctor_id,\n" +
                "\t review_doctor_id, update_time, study_datetime, from_org_no,\n" +
                "\t to_org_no, task_level, create_time \n" +
                "FROM\n" +
                "\t dw_doctor_task_memory\n" +
                "WHERE 1=1\n" +
                "\t AND task_level = 0 \n" +
                "\t AND report_doctor_id IS NULL \n" +
                "\t AND task_status IN ( 101, 102, 201 ) \n" +
                "\t AND to_org_no IN ( 'sr_test_0001_001' ) \n" +
                "\t AND create_time >= '2018-11-11 03:00:00'\n" +
                "\t AND create_time < '2018-11-11 11:59:59' \n" +
                "ORDER BY\n" +
                "create_time DESC limit 50";

        queryWithSql(memorySql);
        System.out.println("memory engine查询100次耗时：" + (new Date().getTime() - start));

        String innodbSql = "SELECT\n" +
                "\t id taskId, task_status, modality, patient_name, accession_no,\n" +
                "\t patient_id, patient_sex, allot_time, ai_tag, study_iuid,\n" +
                "\t reject_tag, reject_date, report_doctor_id,\n" +
                "\t review_doctor_id, update_time, study_datetime, from_org_no,\n" +
                "\t to_org_no, task_level, create_time \n" +
                "FROM\n" +
                "\t dw_doctor_task_innoDB\n" +
                "WHERE 1=1\n" +
                "\t AND task_level = 0 \n" +
                "\t AND report_doctor_id IS NULL \n" +
                "\t AND task_status IN ( 101, 102, 201 ) \n" +
                "\t AND to_org_no IN ( 'sr_test_0001_001' ) \n" +
                "\t AND create_time >= '2018-11-11 03:00:00'\n" +
                "\t AND create_time < '2018-11-11 11:59:59' \n" +
                "ORDER BY\n" +
                "create_time DESC limit 50";

        start = new Date().getTime();
        queryWithSql(innodbSql);
        System.out.println("innodb engine查询100次耗时：" + (new Date().getTime() - start));

//        JdbcTest test = new JdbcTest();
//        PrepareDataTask task = test.new PrepareDataTask();
//        for (int i = 0; i < 10; i++) {
//            executor.submit(task);
//        }
    }

    private static void queryWithSql(String sql) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://39.105.8.99/ris", "root", "Zjy12345+");
            Statement statement = conn.createStatement();

            for (int i = 0; i < 100; i ++) {
                ResultSet rs = statement.executeQuery(sql);

//                while (rs.next()) {
//                    String name = rs.getString("name");
//                    int age = rs.getInt("age");
//
//                    System.out.println("name = " + name + ", age = " + age);
//                }
                rs.close();
            }
            conn.close();
//            prepareData(13000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class PrepareDataTask implements Runnable {
        @Override
        public void run() {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://39.105.8.99/ris", "root", "Zjy12345+");
                PreparedStatement pstmt = conn.prepareStatement("insert into dw_doctor_task_memory ( patient_id, study_iuid, patient_sex, from_org_no, to_org_no, " +
                        "create_time, update_time ) values( ?, ?, ?, ?, ?, ?, ? )");
                Random random = new Random();
                while (count < prepareCount) {
                    String patientId = "patient" + random.nextInt(40000);
                    String studyUid = "studyUid" + random.nextInt(40000);
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
                    try {
                        pstmt.execute();
                    } catch (Exception e) {}

                    count ++;
                }
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * 获取给定范围内的一个随机时间
     */
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

    /**
     * 获取给定时间范围内的一个时间戳
     */
    private static long random(long begin, long end) {
        long rtn = (long) (begin + Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }
}