package lambda;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * zhangjunyang 2018/1/7 15:32
 */
public class StreamsTest {

    private enum Status {
        OPEN, CLOSED
    };

    public static void main(String[] args) {
        final Collection<Task> tasks = Arrays.asList(
            new Task(Status.OPEN, 5),
            new Task(Status.OPEN, 5),
            new Task(Status.CLOSED, 5)
        );

        /**
         * 首先，tasks集合被转换成steam表示；
         * 其次，在steam上的filter操作会过滤掉所有CLOSED的task；
         * 第三，mapToInt操作基于每个task实例的Task::getPoints方法将task流转换成Integer集合；
         * 最后，通过sum方法计算总和，得出最后的结果
         */
        final long totalPoints = tasks
                .stream()
                .filter(task -> task.getStatus() == Status.OPEN)
                .mapToInt(Task::getPoints)
                .sum();

        System.out.println("total points:" + totalPoints);

        final double totalPointsOfParallel = tasks
                .stream()
                .parallel()// 使用parallel方法并行处理所有的task
                .map( task -> task.getPoints() ) // or map( Task::getPoints )
                .reduce( 0, Integer::sum );// 使用reduce方法计算最终的结果

        System.out.println("total points:" + totalPointsOfParallel);

        //根据某些条件对其中的元素分组
        final Map<Status, List<Task>> map = tasks
                .stream()
                .collect( Collectors.groupingBy( Task::getStatus ) );// jdk1.8
        System.out.println( map );
    }

    private static final class Task {
        private final Status status;
        private final Integer points;

        public Task(Status status, Integer points) {
            this.status = status;
            this.points = points;
        }

        public Status getStatus() {
            return status;
        }

        public Integer getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "status=" + status +
                    ", points=" + points +
                    '}';
        }
    }
}

