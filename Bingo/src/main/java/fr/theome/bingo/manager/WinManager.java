package fr.theome.bingo.manager;

import fr.theome.bingo.utils.Team;

import java.util.ArrayList;

public class WinManager {

    //Listes contenant les possibilité de victoire
    public static int[] win1 = {0,1,2,3,4};
    public static int[] win2 = {5,6,7,8,9};
    public static int[] win3 = {10,11,12,13,14};
    public static int[] win4 = {15,16,17,18,19};
    public static int[] win5 = {20,21,22,23,24};
    public static int[] win6= {0, 5, 10, 15, 20};
    public static int[] win7= {1,6,11,16,21};
    public static int[] win8= {2,7,12,17,22};
    public static int[] win9= {3,8,13,18,23};
    public static int[] win10= {4, 9, 14, 19, 24};
    public static int[] win11= {0, 6, 12, 18, 24};
    public static int[] win12= {4, 8, 12, 16, 20};
    public static ArrayList<int[]> wins = new ArrayList<>();


    public static void init(){
        wins.add(win1);
        wins.add(win2);
        wins.add(win3);
        wins.add(win4);
        wins.add(win5);
        wins.add(win6);
        wins.add(win7);
        wins.add(win8);
        wins.add(win9);
        wins.add(win10);
        wins.add(win11);
        wins.add(win12);
    }

    public static boolean checkWinRows(Team team, int nb){
        ArrayList<Boolean> list = team.items_get;
        int rows = 0;
        for (int[] a : wins){
            boolean have_row = false;
            for (int i = 0; i<5;i++){
                if (list.get(a[i])){
                    have_row=true;
                } else {
                    have_row=false;
                    break;
                }
            }
            if (have_row) rows++;
        }
        return rows>=nb;
    }

    public static boolean checkWinItems(Team team, int nb){
        int item = 0;
        for (Boolean bool : team.items_get){
            if (bool) item++;
        }
        return (item>=nb);
    }

}
