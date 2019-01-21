package jdbc;

import com.google.common.collect.Lists;
import designPattern.statePattern.State;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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

    private static final String NO_ALLOT_SQL = "SELECT id taskId, task_status, patient_name, patient_sex, patient_id, modality, ai_tag, accession_no, study_iuid, study_datetime, create_time, from_org_no, to_org_no, abandon_reason, abandon_time, task_level, (CASE WHEN from_org_no = 'sr_test_0001_001' THEN 0 ELSE 1 END) fromTag " +
            "FROM dw_doctor_task dt WHERE 1 = 1 AND create_time >= '2018-11-11 03:00:00' AND create_time < '2018-11-11 11:59:59' AND from_org_no = 'sr_test_0001_001' AND task_status IN (101, 102, 103) order by create_time DESC limit 50";

    private static final String PROCESS_SQL = "SELECT id taskId, task_status, patient_name, patient_sex, patient_id, modality, task_level, allot_time, accession_no, study_datetime, update_time, operate_id, ai_tag, from_org_no, to_org_no, reject_tag, reject_date, report_doctor_id, review_doctor_id, study_iuid, create_time, (CASE WHEN from_org_no = 'sr_test_0001_001' THEN 0 ELSE 1 END) fromTag, (CASE WHEN to_org_no = 'sr_test_0001_001' THEN 0 ELSE 1 END) toTag " +
            "FROM dw_doctor_task dt WHERE 1 = 1 AND create_time >= '2018-11-11 03:00:00' AND create_time < '2018-11-11 11:59:59' AND from_org_no = 'sr_test_0001_001' AND to_org_no = 'sr_test_0001_001' AND task_status IN (301, 302, 303, 304, 305) order by update_time DESC limit 50";

    private static final String OPERATOR_ALL = "SELECT id taskId, patient_name, patient_sex, patient_id, modality, task_status, study_iuid, ai_tag, task_level, accession_no, study_datetime, update_time, from_org_no, to_org_no, create_time, (CASE WHEN from_org_no = 'sr_test_0001_001' THEN 0 ELSE 1 END) fromTag, (CASE WHEN to_org_no = 'sr_test_0001_001' THEN 0 ELSE 1 END) toTag " +
            "FROM dw_doctor_task dt WHERE (from_org_no = 'sr_test_0001_001' OR to_org_no = 'sr_test_0001_001') AND task_status != 402 AND create_time >= '2018-11-11 03:00:00' AND create_time < '2018-11-11 11:59:59' order by update_time DESC limit 50";

    private static final String NO_REPORT = "SELECT * FROM ((SELECT id taskId, task_status, modality, patient_name, accession_no, patient_id, patient_sex, allot_time, ai_tag, study_iuid, reject_tag, reject_date, report_doctor_id, review_doctor_id, update_time, study_datetime, from_org_no, to_org_no, task_level, create_time FROM dw_doctor_task WHERE task_level = 0 AND report_doctor_id = 110110 AND task_status IN (301, 302) AND create_time >= '2018-11-11 03:00:00' AND create_time < '2018-11-11 11:59:59' ORDER BY CASE WHEN report_doctor_id = 110110 THEN 1 ELSE 2 END ASC, study_datetime DESC) " +
            "UNION ALL (SELECT id taskId, task_status, modality, patient_name, accession_no, patient_id, patient_sex, allot_time, ai_tag, study_iuid, reject_tag, reject_date, report_doctor_id, review_doctor_id, update_time, study_datetime, from_org_no, to_org_no, task_level, create_time FROM dw_doctor_task WHERE task_level = 0 AND create_time >= '2018-11-11 03:00:00' AND create_time < '2018-11-11 11:59:59' AND report_doctor_id IS NULL AND task_status IN (101, 102, 201) AND to_org_no IN ('sr_test_0001_001'))) AS TEMP WHERE 1 = 1 AND task_level = 0 AND create_time >= '2018-11-11 03:00:00' AND create_time < '2018-11-11 11:59:59' order by study_datetime limit 50";

    private static final String DOCTOR_ALL = "SELECT id taskId, task_status, modality, patient_name, accession_no, patient_id, patient_sex, allot_time, ai_tag, study_iuid, reject_tag, reject_date, report_doctor_id, review_doctor_id, update_time, study_datetime, from_org_no, to_org_no, task_level, create_time " +
            "FROM dw_doctor_task WHERE task_status < 402 AND (from_org_no IN ('sr_test_0001_001') OR to_org_no IN ('sr_test_0001_001')) AND create_time >= '2018-11-11 03:00:00' AND create_time < '2018-11-11 11:59:59' order by study_datetime desc limit 50";

    private static final String INSERT = "INSERT INTO dw_doctor_task(task_status, modality, patient_name, accession_no, patient_id, patient_sex, allot_time, ai_tag, study_iuid, reject_tag, reject_date, report_doctor_id, review_doctor_id, update_time, study_datetime, from_org_no, to_org_no, task_level, create_time) " +
            "VALUES (101, NULL, NULL, NULL, patientId, 'M', NULL, 0, studyUid, 0, NULL, NULL, NULL, updateTime, NULL, 'sr_test_0001_001', 'sr_test_0001_001', 0, createTime)";

    public static void main(String[] args) throws Exception {
        List<String> sqlList = Lists.newArrayList(NO_ALLOT_SQL, PROCESS_SQL, OPERATOR_ALL, NO_REPORT, DOCTOR_ALL);
        long start = new Date().getTime();
//        for (String sql : sqlList) {
//            String memorySql = sql.replace("dw_doctor_task", "dw_doctor_task_memory");

//            queryWithSql(memorySql, 100);
//            System.out.println("memory engine查询100次耗时：" + (new Date().getTime() - start));
//
            //and create_time <= '2018-12-12 14:00:00' and create_time > '2018-12-12 12:00:00'
            //and accession_no like '%CT18%'
            //and patient_id like '%2914%'
            //and study_datetime <= '2018-5-21 15:35:00' and study_datetime > '2018-5-21 15:30:00'
            String innodbSql = "SELECT * FROM ((SELECT id taskId, task_status, modality, patient_name, accession_no, patient_id, patient_sex, allot_time, ai_tag, study_iuid, reject_tag, reject_date, report_doctor_id, review_doctor_id, update_time, study_datetime, from_org_no, to_org_no, task_level, create_time FROM dw_doctor_task WHERE task_level = 0 AND report_doctor_id = 100209 AND task_status IN (301, 302) AND create_time <= '2018-12-12 14:00:00' and create_time > '2018-12-12 12:00:00' ORDER BY CASE WHEN report_doctor_id = 100209 THEN 1 ELSE 2 END ASC, study_datetime DESC) UNION ALL (SELECT id taskId, task_status, modality, patient_name, accession_no, patient_id, patient_sex, allot_time, ai_tag, study_iuid, reject_tag, reject_date, report_doctor_id, review_doctor_id, update_time, study_datetime, from_org_no, to_org_no, task_level, create_time FROM dw_doctor_task WHERE task_level = 0 AND create_time <= '2018-12-12 14:00:00' and create_time > '2018-12-12 12:00:00' AND report_doctor_id IS NULL AND task_status IN (101, 102, 201) AND to_org_no IN ('sr_test_0009_001'))) AS TEMP WHERE 1 = 1 AND task_level = 0 AND create_time <= '2018-12-12 14:00:00' and create_time > '2018-12-12 12:00:00' and patient_id like '%2914%'  order by create_time limit 50";

            start = new Date().getTime();
            queryWithSql(innodbSql, 100);
            System.out.println("innodb engine查询100次耗时：" + (new Date().getTime() - start));

            System.out.println("------------------------");
//        }

//        start = new Date().getTime();
//        String memoryInsert = INSERT.replace("dw_doctor_task", "dw_doctor_task_memory");
//        insertWithSql(memoryInsert, 50);
//        System.out.println("memory engine插入50条数据耗时：" + (new Date().getTime() - start));
//        start = new Date().getTime();
//        String innodbInsert = INSERT.replace("dw_doctor_task", "dw_doctor_task_innoDB");
//        insertWithSql(innodbInsert, 50);
//        System.out.println("innodb engine插入50条数据耗时：" + (new Date().getTime() - start));

        Thread.sleep(1000);
//        JdbcTest test = new JdbcTest();
//        PrepareDataTask task = test.new PrepareDataTask();
//        for (int i = 0; i < 10; i++) {
//            executor.submit(task);
//        }
    }

    private static void insertWithSql(String sql, int num) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://39.105.8.99/ris", "root", "Zjy12345+");
            Statement statement = conn.createStatement();

            Random random = new Random();
            for (int i = 0; i < num; i ++) {
                String date = LocalDateTime.now().toString();
                String newSql = sql.replace("patientId", "\'" + "test" + random.nextInt() + "\'")
                        .replace("studyUid", "\'" + "test" + random.nextInt() + "\'")
                        .replace("createTime", "\'" + date + "\'")
                        .replace("updateTime", "\'" + date + "\'");
//                System.out.println(newSql);
                statement.executeUpdate(newSql);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void queryWithSql(String sql, int num) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://172.16.10.38/dw_cloud_ris", "root", "deepwise");
            Statement statement = conn.createStatement();

            for (int i = 0; i < num; i ++) {
                Date startDate = new Date();
                ResultSet rs = statement.executeQuery(sql);
                System.out.println("第" + (i+1) + "次执行耗时: " + (new Date().getTime() - startDate.getTime()));

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

                    pstmt.setString(1, patientId);
                    pstmt.setString(2, studyUid);
                    pstmt.setString(3, patientSex);
                    pstmt.setString(4, orgNo);
                    pstmt.setString(5, orgNo);
                    pstmt.setTimestamp(6, new java.sql.Timestamp(createTime.getTime()));
                    pstmt.setTimestamp(7, new java.sql.Timestamp(createTime.getTime()));
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

        /**
         * 获取给定范围内的一个随机时间
         */
        private Date getRandomDate(String beginDate, String endDate) throws Exception {
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
        private long random(long begin, long end) {
            long rtn = (long) (begin + Math.random() * (end - begin));
            if (rtn == begin || rtn == end) {
                return random(begin, end);
            }
            return rtn;
        }
    }
}
