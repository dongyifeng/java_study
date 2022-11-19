package sorted_table;

import java.util.*;


public class select_job {

    public static class Job {
        // 工作报酬
        public int money;
        // 工作难度
        public int hard;

        public Job(int money, int hard) {
            this.money = money;
            this.hard = hard;
        }
    }

    public static class JobComparator implements Comparator<Job> {

        @Override
        public int compare(Job o1, Job o2) {
            return o1.hard != o2.hard ? o1.hard - o2.hard : o2.money - o1.money;
        }
    }

    public static int[] getMoneys(Job[] jobs, int[] ability) {
        Arrays.sort(jobs, new JobComparator());
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        int money = 0;
        for (Job job : jobs) {
            if (treeMap.containsKey(job.hard) || job.money <= money) {
                continue;
            }
            money = Math.max(money, job.money);
            treeMap.put(job.hard, job.money);
        }

        int[] res = new int[ability.length];

        for (int i = 0; i < ability.length; i++) {
            int item = ability[i];
            Map.Entry<Integer, Integer> entry = treeMap.floorEntry(item);
            if (entry == null) {
                continue;
            }
            res[i] = entry.getValue();
        }
        return res;
    }

    public static int[] getMoneys2(Job[] jobs, int[] ability) {
        Arrays.sort(jobs, new JobComparator());
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        Job pre = jobs[0];
        for (int i = 1; i < jobs.length; i++) {
            if (jobs[i].hard != pre.hard && jobs[i].money > pre.money) {
                treeMap.put(jobs[i].hard, jobs[i].money);
                pre = jobs[i];
            }
        }

        int[] res = new int[ability.length];

        for (int i = 0; i < ability.length; i++) {
            int item = ability[i];
            Map.Entry<Integer, Integer> entry = treeMap.floorEntry(item);
            if (entry == null) {
                continue;
            }
            res[i] = entry.getValue();
        }
        return res;
    }


    public static void main(String[] args) {
        Job[] jobs = new Job[8];
        jobs[0] = new Job(5, 3);
        jobs[1] = new Job(7, 2);
        jobs[2] = new Job(100, 9);
        jobs[3] = new Job(4, 1);
        jobs[4] = new Job(6, 2);
        jobs[5] = new Job(3, 3);
        jobs[6] = new Job(1, 1);
        jobs[7] = new Job(8, 2);
        int[] ability = new int[]{0, 2, 8, 9, 10};

        for (int item : getMoneys(jobs, ability)) {
            System.out.println(item);
        }

        System.out.println("-------------");
        for (int item : getMoneys2(jobs, ability)) {
            System.out.println(item);
        }
    }
}
