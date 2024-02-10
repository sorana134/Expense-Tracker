package phase1;

import java.util.StringTokenizer;

public class CostOperations {

        public boolean budgetCheck(int budget, int expense, int cost) {
            if (budget < (expense + cost))
                return true;
            return false;
        }

        public int getExpense(String temp) {
            StringTokenizer st = new StringTokenizer(temp, " ");
            String[] a = { "0", "0" };
            int i = 0;
            while (st.hasMoreTokens()) {
                a[i] = st.nextToken();
                i++;
            }
            return Integer.parseInt(a[1]);
        }

        public int getBudget(String temp) {
            StringTokenizer st = new StringTokenizer(temp, " ");
            String[] a = { "0", "0" };
            int i = 0;
            while (st.hasMoreTokens()) {
                a[i] = st.nextToken();
                i++;
            }
            return Integer.parseInt(a[0]);
        }


}
