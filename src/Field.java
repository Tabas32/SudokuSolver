
import java.util.ArrayList;
import java.util.List;

public class Field {

    private ArrayList<Integer>[][] field;
    private ArrayList<Integer> markedBoxes;

    public Field(){
        markedBoxes = new ArrayList<Integer>(81);
        field = new ArrayList[9][9];

        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                List box = new ArrayList<Integer>(9);
                box.add(new Integer(1));
                box.add(new Integer(2));
                box.add(new Integer(3));
                box.add(new Integer(4));
                box.add(new Integer(5));
                box.add(new Integer(6));
                box.add(new Integer(7));
                box.add(new Integer(8));
                box.add(new Integer(9));
                field[row][col] = (ArrayList<Integer>) box;
            }
        }
    }

    public Field(int[] initials){
        this();

        for(int num : initials){
            int row = num / 100 - 1;
            num %= 100;
            int col = num / 10 - 1;
            int boxNum = num % 10;

            for(int i = 1; i < 10; i++){
                if(i != boxNum){
                    removeBoxOption(row, col, new Integer(i));
                }
            }
        }
    }

    private void removeBoxOption(int row, int col, Integer option){
        field[row][col].remove(option);

        if(field[row][col].size() > 1)
            return;

        if(!markedBoxes.contains(row * 9 + col)) markedBoxes.add(row * 9 + col);
        option = field[row][col].get(0);
        for(int i = 0; i < 9; i++){
            if(i != col) field[row][i].remove(option);
            if(i != row) field[i][col].remove(option);
        }

        int smallBoxRow = row / 3;
        int smallBoxCol = col / 3;

        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                int rowToRem = smallBoxRow * 3 + r;
                int colToRem = smallBoxCol * 3 + c;

                if(rowToRem == row && colToRem == col) continue;
                field[rowToRem][colToRem].remove(option);
            }
        }
    }

    public int getLeastOptions(){
        int minSize = 10;
        int toReturn = -1;

        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                int currentSize = field[row][col].size();
                if(!markedBoxes.contains(row * 9 + col) && currentSize < minSize){
                    minSize = currentSize;
                    toReturn = row * 9 + col;
                }
            }
        }

        return toReturn;
    }

    private void markFirstOption(int boxPosition) {
        int row = boxPosition / 9;
        int col = boxPosition % 9;

        ArrayList<Integer> box = field[row][col];
        if(box.size() == 1) {
            this.removeBoxOption(row, col, null);
            return;
        }

        while (box.size() > 1) {
            this.removeBoxOption(row, col, box.get(1));
        }
    }

    public void solve(){
        int leastOptPos = this.getLeastOptions();

        while(leastOptPos > -1){
            this.markFirstOption(leastOptPos);
            leastOptPos = this.getLeastOptions();
        }
    }

    public static void main(String[] args){
        int[] init = {129, 137, 148, 212, 246, 254, 263, 299, 326, 382, 428, 449, 522, 557, 589, 661, 683, 725, 781, 811, 843, 858, 862, 894, 966, 979, 988};
        Field field = new Field(init);

        System.out.println(field);
        System.out.println("!!!!!!!!!!!!!!!!!!!SOLVING!!!!!!!!!!!!!!!!!!!!!");
        field.solve();
        System.out.println(field);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                sb.append("<");
                for(Integer num : field[row][col]){
                    sb.append(num).append(",");
                }
                sb.append("> ");
                if(col % 3 == 2){
                    sb.append(" | ");
                }
            }
            sb.append("\n");
            if(row % 3 == 2){
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
