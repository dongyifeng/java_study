package other;

import java.util.*;

/*

 */
public class Problem04_CoverMax {
    public static class Rectangle {
        public int up;
        public int down;
        public int left;
        public int right;

        public Rectangle(int up, int down, int left, int right) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }
    }

    public static class DownComparator implements Comparator<Rectangle> {

        @Override
        public int compare(Rectangle o1, Rectangle o2) {
            return o1.down - o2.down;
        }
    }

    public static class LeftComparator implements Comparator<Rectangle> {

        @Override
        public int compare(Rectangle o1, Rectangle o2) {
            return o1.left - o2.left;
        }
    }


    public static class RightComparator implements Comparator<Rectangle> {

        @Override
        public int compare(Rectangle o1, Rectangle o2) {
            return o1.right - o2.right;
        }
    }

    public static int maxCover(Rectangle[] recs) {
        if (recs == null || recs.length == 0) {
            return 0;
        }
        Arrays.sort(recs, new DownComparator());


        TreeSet<Rectangle> leftOrdered = new TreeSet<>(new LeftComparator());
        int res = 0;
        for (int i = 0; i < recs.length; i++) {
            int curDown = recs[i].down;
            int index = i;
            while (recs[index].down == curDown) {
                leftOrdered.add(recs[index]);
                index++;
            }
            i = index;
            removeLowerOnCurDown(leftOrdered, curDown);
            TreeSet<Rectangle> rightOrdered = new TreeSet<>(new RightComparator());
            for (Rectangle rec : leftOrdered) {
                removeLeftOnCurLeft(rightOrdered, rec.left);
                rightOrdered.add(rec);
                res = Math.max(res, rightOrdered.size());
            }
        }
        return res;
    }

    public static void removeLowerOnCurDown(TreeSet<Rectangle> set, int curDown) {
        List<Rectangle> removes = new ArrayList<>();
        for (Rectangle rec : set) {
            if (rec.up <= curDown) {
                removes.add(rec);
            }
        }
        for (Rectangle rec : removes) {
            set.remove(rec);
        }
    }

    public static void removeLeftOnCurLeft(TreeSet<Rectangle> rightOrdered, int curLeft) {
        List<Rectangle> removes = new ArrayList<>();
        for (Rectangle rec : rightOrdered) {
            if (rec.right > curLeft) {
                break;
            }
            removes.add(rec);
        }
        for (Rectangle rec : removes) {
            rightOrdered.remove(rec);
        }
    }

    public static void main(String[] args) {
        // int up, int down, int left, int right
        TreeSet<Rectangle> rightOrdered = new TreeSet<>(new RightComparator());
        Rectangle rectangle = new Rectangle(2, 0, 1, 3);
        Rectangle rectangle2 = new Rectangle(1, 0, 2, 3);


        Rectangle[] recs= new Rectangle[2];
        recs[0]=rectangle;
        recs[1]=rectangle;
        System.out.println(maxCover(recs));


    }
}
