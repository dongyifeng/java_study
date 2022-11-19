import org.junit.Test;

import java.util.ArrayList;

public class TestCollection {
    @Test
    public void test() {
//        String[] str = new String[5];
//        for (String myStr : str) {
//            myStr = "atguigu";
//            System.out.println(myStr);
//        }
//        for (int i = 0; i < str.length; i++) {
//            System.out.println(str[i]);
//        }


        ArrayList<ListNode> list = new ArrayList<ListNode>();
        list.add(new ListNode("a").next = new ListNode("b"));
        list.add(new ListNode("c").next = new ListNode("d"));

        list.forEach(item -> {
            item.next = new ListNode(item.val);
        });

        list.forEach((item) -> {
            while (item != null) {
                System.out.print(item.val + "->");
                item = item.next;
            }

            System.out.println("----");
        });
    }

    public class ListNode {
        public String val;
        public ListNode next;

        public ListNode(String val) {
            this.val = val;
        }
    }
}
