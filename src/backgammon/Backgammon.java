/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammon;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Süleyman Behlül UYSAL
 */
class OddEvenRunnable implements Runnable{
 
	public volatile int gamestatus=1;
        private 
	static int  number=1;
        String temp;
        int random1,random2,turn;
	int remainder;
	static Object lock=new Object();
        String[] checkers;
	OddEvenRunnable(int remainder, String[] checkers)
	{
		this.remainder=remainder;
                this.checkers = checkers;
	}
 
	@Override
	public void run() {
		while (gamestatus == 1) {
                    int a = 0;
                    int b = 0;
                    for(int x = 0; x<24;x++)
                    {
                        if(checkers[x].contains("w"))
                            a = 1;
                        if(checkers[x].contains("r"))
                            b = 1;
                    }
                    if(a == 0 || b == 0)
                    {
                        gamestatus = 0;
                        break;
                    }
			synchronized (lock) {
				while (number % 2 != remainder) { // wait for numbers other than remainder
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
                                 for(int j = 0;j<24;j++)
                                        {
                                            if(!checkers[j].contains("r"))
                                                    gamestatus = 0;
                                            else
                                            {
                                                gamestatus = 1;
                                                break;
                                            }
                                        }
                                 if(gamestatus == 1)
                                  for(int j = 0;j<24;j++)
                                        {
                                            if(!checkers[j].contains("w"))
                                                    gamestatus = 0;
                                            else
                                            {
                                                gamestatus = 1;
                                                break;
                                            }
                                        }
                                if((remainder == 0 && b == 1)&&  gamestatus == 1)
                                {
                                    //red
                                    int flag = 0;
                                    int flag2 = 0;
                                    int counter = 0;
                                    int kirik=0;
                                    int turn1 = 0;
                                    char[] kiriklar = checkers[24].toCharArray();
                                    //(int)(Math.random() * (6 - 1 + 1) + 1)
                                    random1 = (int)(Math.random() * (6 - 1 + 1) + 1);
                                    random2 = (int)(Math.random() * (6 - 1 + 1) + 1);
                                    System.out.println(Thread.currentThread().getName() + " playing " + number+". move. Dices rolled: "+random1+" , "+random2);
                                    if(random1==random2)
                                        turn=4;
                                    else
                                        turn=2;
                                    //turn = checkMove(checkers,"r",random1,random2);
                                    //System.out.println("Possible moves: "+turn);
                                    for(int x = 0; x<checkers[24].length();x++)
                                    {
                                        if(kiriklar[x] == 'r')
                                        kirik++;
                                    }
                                    if(kirik > 0)
                                    {
                                        for(int tempturn=0; tempturn<turn;tempturn++)
                                        {
                                            for(int w = 0; w<6;w++)
                                            {
                                                if((checkers[w].length() <= 1 && kirik > 0 && random1-1== w) || (checkers[w].contains("r") && kirik >0 && random1-1 == w))
                                                {
                                                    if(checkers[w].length() == 1 && checkers[w].contains("w"))
                                                    {
                                                       checkers[25]+="w"; 
                                                       checkers[w] = "";
                                                    }  
                                                    kirik--;
                                                    checkers[w] += "r";
                                                    turn1++;
                                                    checkers[24]=checkers[24].substring(0,checkers[24].length()-1);
                                                    if(turn == 2)
                                                        random1=0;
                                                    if(turn == 4 && turn1 == 4)
                                                    {
                                                        random1 = 0;
                                                        random2 = 0;
                                                    }
                                                }
                                                else if(checkers[w].length() <= 1 && kirik > 0 && random2-1== w || (checkers[w].contains("r") && kirik >0 && random2-1 == w))
                                                {
                                                    if(checkers[w].length() == 1&& checkers[w].contains("w"))
                                                    {
                                                        checkers[25]+="w";
                                                        checkers[w] = "";
                                                    }
                                                        
                                                    kirik--;
                                                    checkers[w] += "r";
                                                    turn1++;
                                                    checkers[24]=checkers[24].substring(0,checkers[24].length()-1);
                                                    random2 = 0;
                                                }
                                            }
                                        }
                                       
                                    }
                                    for(; turn1<turn;)
                                    {
                                       for(int j = 0;j<24;j++)
                                        {
                                            if(!checkers[j].contains("r"))
                                                    gamestatus = 0;
                                            else
                                            {
                                                gamestatus = 1;
                                                break;
                                            }
                                        }
                                        for(int j=0;j<24;j++)
                                            {
                                                if(checkers[j].contains("r") && j<18)
                                                {
                                                    flag = 0;
                                                    break;
                                                }
                                                else
                                                    flag = 1; // toplama geçtik
                                            }
                                        int max = 0;
                                        if(random1 <= random2)
                                            max = random1;
                                        else
                                            max = random2;
                                        for(int i = 0; i<24;i++)
                                        {
                                            if((flag == 0 || flag2 == 2) && gamestatus == 1 && checkers[24].length() == 0)
                                            {
                                                if(turn1 == turn)
                                                break;
                                                
                                                if(i+random1 <=23)
                                                if(checkers[i].contains("r")&& !checkers[i+random1].contains("ww") && random1 != 0)
                                                {
                                                    if("w".equals(checkers[i+random1]))
                                                    {
                                                       checkers[25]+="w";
                                                       checkers[i+random1]="";
                                                    } 
                                                    checkers[i+random1] += "r";
                                                    checkers[i] = checkers[i].substring(0, checkers[i].length()-1);
                                                    turn1++;
                                                     if(turn == 2)
                                                        random1=0;
                                                    if(turn == 4 && counter == 4)
                                                    {
                                                      random1=0;
                                                      random2 = 0;
                                                    }
                                                    counter++;
                                                    break;
                                                }
                                                if(i+random2 <=23)
                                                if(checkers[i].contains("r")&& !checkers[i+random2].contains("ww")&& random2 != 0)
                                                {
                                                    if("w".equals(checkers[i+random2]))
                                                    {
                                                       checkers[25]+="w";
                                                       checkers[i+random2]="";
                                                    } 
                                                    checkers[i+random2] += "r";
                                                    checkers[i] = checkers[i].substring(0, checkers[i].length()-1);
                                                    turn1++;
                                                    random2=0;
                                                    if(flag2 == 2)
                                                        flag2 = 0;
                                                    break;
                                                }
                                            }
                                            else if(flag == 1&& kirik == 0)
                                            {
                                                int sayac = 0;
                                                if(turn1 == turn)
                                                    break;
                                                for(int j = turn1; j<turn; j++)
                                                {
                                                    for(int k = 18; k<=23;k++)
                                                    {
                                                        sayac = 0;
                                                        for(int loop = 5; loop > k; loop--)
                                                        {
                                                            if(checkers[loop].contains("w"))
                                                                sayac = 1;
                                                        }
                                                        if((k>=24-random1 && !"".equals(checkers[k]) && random1 != 0 && sayac == 0) || (k == 24-random1 && !"".equals(checkers[k])))
                                                        {
                                                            checkers[k] = checkers[k].substring(0,checkers[k].length()-1);
                                                            counter++;
                                                            turn1++;
                                                            if(turn == 2)
                                                                random1=0;
                                                            if(turn == 4 && counter == 4)
                                                            {
                                                              random1=0;
                                                              random2 = 0;
                                                            }
                                                            break;
                                                        }
                                                        else if((k>=24-random2 && !"".equals(checkers[k]) && random2!=0 && sayac == 0) || (k == 24-random2 && !"".equals(checkers[k])))
                                                        {
                                                            checkers[k] = checkers[k].substring(0,checkers[k].length()-1);
                                                            counter++;
                                                            turn1++;
                                                            if(turn == 2)
                                                                random2=0;
                                                            break;
                                                        }
                                                        else
                                                            flag2 = 2;
                                                    }
                                                      int test=0;
                                                        for(int kontrol = 0; kontrol<24;kontrol++)
                                                        {
                                                            if(checkers[kontrol].contains("r"))
                                                            {
                                                                test = 1;
                                                                
                                                                break;
                                                            } 
                                                        }
                                                         if(test == 0)
                                                            {
                                                                turn1 = turn;
                                                                gamestatus = 0;
                                                                placecheckers(checkers);
                                                                System.out.println("Game finisjed. Red wins!");
                                                                break;
                                                            }
                                                }
                                                    
                                            }
                                            if(i+1 == 23)
                                                turn1++;
                                        }
                                       
                                    }
                                   
                                }
                                else if((remainder == 1 && a == 1)&& gamestatus == 1)
                                {
                                    //white
                                    int flag = 0;
                                    int flag2 = 0;
                                    int counter = 0;
                                    int kirik = 0;
                                    int turn1 = 0;
                                    char[] kiriklar = checkers[25].toCharArray();
                                    random1 = (int)(Math.random() * (6 - 1 + 1) + 1);
                                    random2 = (int)(Math.random() * (6 - 1 + 1) + 1);
                                    System.out.println(Thread.currentThread().getName() + " playing " + number+". move. Dices rolled: "+random1+" , "+random2);
                                    if(random1==random2)
                                        turn=4;
                                    else
                                        turn=2;
                                    //turn = checkMove(checkers,"w",random1,random2);
                                    //System.out.println("Possible moves: "+turn);
                                    
                                     for(int x = 0; x<checkers[25].length();x++)
                                    {
                                        if(kiriklar[x] == 'w')
                                        kirik++;
                                    }
                                    if(kirik > 0)
                                    {
                                        for(int tempturn=0; tempturn<turn;tempturn++)
                                        {
                                            for(int w = 18; w<24;w++)
                                            {
                                                if((checkers[w].length() <= 1 && kirik > 0 && 24-random1== w) || (checkers[w].contains("w") && kirik >0 && 24-random1 == w))
                                                {
                                                    if(checkers[w].length() == 1 && checkers[w].contains("r"))
                                                    {
                                                        checkers[24]+="r";
                                                        checkers[w] = "";
                                                    }
                                                    kirik--;
                                                    checkers[w] += "w";
                                                    turn1++;
                                                    checkers[25]=checkers[25].substring(0,checkers[25].length()-1);
                                                    if(turn == 2)
                                                        random1=0;
                                                    if(turn == 4 && turn1 == 4)
                                                    {
                                                        random1 = 0;
                                                        random2 = 0;
                                                    }
                                                }
                                                else if((checkers[w].length() <= 1 && kirik > 0 && 24-random2== w) || (checkers[w].contains("w") && kirik >0 && 24-random2 == w))
                                                {
                                                    if(checkers[w].length() == 1 && checkers[w].contains("r"))
                                                    {
                                                        checkers[24]+="r";
                                                        checkers[w] = "";
                                                    }
                                                        
                                                    kirik--;
                                                    checkers[w] += "w";
                                                    turn1++;
                                                    checkers[25]=checkers[25].substring(0,checkers[25].length()-1);
                                                    random2 = 0;
                                                }
                                            }
                                        }
                                       
                                    }
                                    
                                    for(; turn1<turn;)
                                    {
                                        for(int j = 0;j<24;j++)
                                        {
                                            if(!checkers[j].contains("w"))
                                                    gamestatus = 0;
                                            else
                                            {
                                                gamestatus = 1;
                                                break;
                                            }
                                        }
                                        for(int j = 23;j>=0;j--)
                                            {
                                                if(checkers[j].contains("w") && j>5)
                                                {
                                                    flag = 0;
                                                    break;
                                                }
                                                else
                                                    flag = 1; // toplama geçtik
                                            }
                                        int max = 0;
                                        if(random1 >= random2)
                                            max = random1;
                                        else
                                            max = random2;
                                        for(int i = 23; i>0;i--)
                                        {
                                           
                                            if((flag == 0 || flag2 == 2) && gamestatus == 1 && checkers[25].length() == 0)
                                            {
                                                if(turn1 == turn)
                                                     break;
                                                if(i-random1 >=0)
                                                if(checkers[i].contains("w")&& !checkers[i-random1].contains("rr") && random1 != 0)
                                                {
                                                    if("r".equals(checkers[i-random1]))
                                                    {
                                                       checkers[24]+="r";
                                                       checkers[i-random1]="";
                                                    }  
                                                    checkers[i-random1] += "w";
                                                    checkers[i] = checkers[i].substring(0, checkers[i].length()-1);
                                                    turn1++;
                                                    if(turn == 2)
                                                        random1=0;
                                                    if(turn == 4 && counter == 4)
                                                    {
                                                        random1 = 0;
                                                        random2 = 0;
                                                    }
                                                    counter++;
                                                    break;
                                                }
                                                if(i-random2>=0)
                                                if(checkers[i].contains("w")&& !checkers[i-random2].contains("rr")&& random2 != 0)
                                                {
                                                    if("r".equals(checkers[i-random2]))
                                                    {
                                                       checkers[24]+="r";
                                                       checkers[i-random2]="";
                                                    } 
                                                    checkers[i-random2] += "w";
                                                    checkers[i] = checkers[i].substring(0, checkers[i].length()-1);
                                                    turn1++;
                                                    random2=0;
                                                    
                                                    break;
                                                }
                                                if(flag2 == 2 && turn1 == turn)
                                                    flag2=0;
                                                
                                            }
                                            else if(flag == 1 && kirik == 0)
                                            {
                                                int sayac = 0;
                                                if(turn1 == turn)
                                                    break;
                                                 for(int j = turn1; j<turn; j++)
                                                {
                                                    for(int k = 5; k>=0;k--)
                                                    {
                                                        sayac = 0;
                                                        for(int loop = 5; loop > k; loop--)
                                                        {
                                                            if(checkers[loop].contains("w"))
                                                                sayac = 1;
                                                        }
                                                        if((k<=random1-1 && !"".equals(checkers[k]) && random1 != 0 && sayac == 0) || (k==random1-1 && !"".equals(checkers[k])))
                                                        {
                                                            checkers[k] = checkers[k].substring(0,checkers[k].length()-1);
                                                            counter++;
                                                            turn1++;
                                                            if(turn == 2)
                                                                random1=0;
                                                            if(turn == 4 && counter == 4)
                                                            {
                                                              random1=0;
                                                              random2 = 0;
                                                            }
                                                            break;
                                                        }
                                                        else if(k<=random2-1 && !"".equals(checkers[k]) && random2!=0&& sayac == 0|| (k==random2-1 && !"".equals(checkers[k])))
                                                        {
                                                            checkers[k] = checkers[k].substring(0,checkers[k].length()-1);
                                                            counter++;
                                                             turn1++;
                                                            if(turn == 2)
                                                                random2=0;
                                                             break;
                                                        }
                                                        else
                                                        {
                                                            flag2=2;
                                                        }
                                                        
                                                    }
                                                    int test=0;
                                                        for(int kontrol = 0; kontrol<24;kontrol++)
                                                        {
                                                            if(checkers[kontrol].contains("w"))
                                                            {
                                                                test = 1;
                                                                
                                                                break;
                                                            } 
                                                        }
                                                         if(test == 0)
                                                            {
                                                                flag = 10;
                                                                flag2 = 10;
                                                                turn1 = turn;
                                                                gamestatus = 0;
                                                                placecheckers(checkers);
                                                                System.out.println("Game finisjed. White wins!");
                                                                
                                                                break;
                                                            }
                                                }
                                            }
                                            if(i-1 == 0)
                                                turn1++;
                                        }
                                       
                                    }
                                    
                                   
                                }
                                
				number++;
				lock.notifyAll();
                                
                                if(gamestatus == 1)
                                {
                                    placecheckers(checkers);
                                    System.out.println("Broken checkers: "+checkers[24]+" "+checkers[25]);
                                }
                                try {
                            Thread.sleep(2000);
                            //String userName = myObj.nextLine();  // Read user input
                            } catch (InterruptedException ex) {
                                Logger.getLogger(OddEvenRunnable.class.getName()).log(Level.SEVERE, null, ex);
                            }

			}
		}
	}

    private void placecheckers(String[] checkers) {
        String operand = "";
        int temp;
        System.out.print(" ___________________________________________ \n"
                       + "|                   |   |                   |\n"
                       + "| 13 14 15 16 17 18 |   | 19 20 21 22 23 24 |\n");
        for(int i = 1; i<6;i++)
        {
            System.out.print("| ");
            for(int y = 12;y<18;y++)
            {
                if(checkers[y].contains("w"))
                    operand = "w";
                else if(checkers[y].contains("r"))
                    operand = "r";
                 
                if(checkers[y].length()>= i)
                {
                    if(checkers[y].length() > 10)
                    {
                        temp = checkers[y].length()-10;
                        if(temp>=i)
                            System.out.print(operand.toUpperCase()+operand+" ");
                        else if(i<=5)
                            System.out.print(operand+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length()>5 && checkers[y].length() < 10)
                    {
                        temp = checkers[y].length()-5;
                        if(temp>=i)
                            System.out.print(operand+operand+" ");
                        else if(i<=5)
                            System.out.print(" "+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length() == 10)
                    {
                        if(i<=5)
                            System.out.print(operand+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length()<=5 && checkers[y].length() > 0)
                    {
                        System.out.print(" "+operand+" ");
                    }
                    else
                    {
                        System.out.print(" "+" "+" ");
                    }
                }
                else
                {
                    System.out.print("   ");
                }
            }
            System.out.print("|   | ");
             for(int y = 18;y<24;y++)
            {
                 if(checkers[y].contains("w"))
                    operand = "w";
                else if(checkers[y].contains("r"))
                    operand = "r";
                 
                if(checkers[y].length()>= i)
                {
                    if(checkers[y].length() > 10)
                    {
                        temp = checkers[y].length()-10;
                        if(temp>=i)
                            System.out.print(operand.toUpperCase()+operand+" ");
                        else if(i<=5)
                            System.out.print(operand+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length()>5 && checkers[y].length() < 10)
                    {
                        temp = checkers[y].length()-5;
                        if(temp>=i)
                            System.out.print(operand+operand+" ");
                        else if(i<=5)
                            System.out.print(" "+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length() == 10)
                    {
                        if(i<=5)
                            System.out.print(operand+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length()<=5 && checkers[y].length() > 0)
                    {
                        System.out.print(" "+operand+" ");
                    }
                    else
                    {
                        System.out.print(" "+" "+" ");
                    }
                }
                else
                {
                    System.out.print("   ");
                }
            }
             System.out.print("|\n");
        }
        
        
        System.out.print("|                   |BAR|                   |\n");
        
        for(int i = 6; i>0;i--)
        {
            System.out.print("| ");
            for(int y = 11;y>5;y--)
            {
                if(checkers[y].contains("w"))
                    operand = "w";
                else if(checkers[y].contains("r"))
                    operand = "r";
                 
                if(checkers[y].length()>= i)
                {
                    if(checkers[y].length() > 10)
                    {
                        temp = checkers[y].length()-10;
                        if(temp>=i)
                            System.out.print(operand.toUpperCase()+operand+" ");
                        else if(i<=5)
                            System.out.print(operand+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length()>5 && checkers[y].length() < 10)
                    {
                        temp = checkers[y].length()-5;
                        if(temp>=i)
                            System.out.print(operand+operand+" ");
                        else if(i<=5)
                            System.out.print(" "+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length() == 10)
                    {
                        if(i<=5)
                            System.out.print(operand+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length()<=5 && checkers[y].length() > 0)
                    {
                        System.out.print(" "+operand+" ");
                    }
                    else
                    {
                        System.out.print(" "+" "+" ");
                    }
                }
                else
                {
                    System.out.print("   ");
                }
            }
            System.out.print("|   | ");
            for(int y = 5;y>=0;y--)
            {
                 if(checkers[y].contains("w"))
                    operand = "w";
                else if(checkers[y].contains("r"))
                    operand = "r";
                 
                if(checkers[y].length()>= i)
                {
                    if(checkers[y].length() > 10)
                    {
                        temp = checkers[y].length()-10;
                        if(temp>=i)
                            System.out.print(operand.toUpperCase()+operand+" ");
                        else if(i<=5)
                            System.out.print(operand+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length()>5 && checkers[y].length() < 10)
                    {
                        temp = checkers[y].length()-5;
                        if(temp>=i)
                            System.out.print(operand+operand+" ");
                        else if(i<=5)
                            System.out.print(" "+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length() == 10)
                    {
                        if(i<=5)
                            System.out.print(operand+operand+" ");
                        else
                            System.out.print(" "+" "+" ");
                    }
                    else if(checkers[y].length()<=5 && checkers[y].length() > 0)
                    {
                        System.out.print(" "+operand+" ");
                    }
                    else
                    {
                        System.out.print(" "+" "+" ");
                    }
                }
                else
                {
                    System.out.print("   ");
                }
            }
             System.out.print("|\n");
        }
         System.out.print("| 12 11 10  9  8  7 |   |  6  5  4  3  2  1 |\n"
                       + "|___________________|___|___________________|\n");
    }
}

public class Backgammon {
    static String[] checkers = {"rr", "", "", "", "", "wwwww", "", "www", "", "", "", "rrrrr",
                        "wwwww", "", "", "", "rrr", "", "rrrrr", "", "", "", "", "ww","",""};
    
    int turn = -1;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    
    
    
    
                OddEvenRunnable oddRunnable=new OddEvenRunnable(1,checkers);
		OddEvenRunnable evenRunnable=new OddEvenRunnable(0,checkers);
 
		Thread t1=new Thread(oddRunnable,"White");
		Thread t2=new Thread(evenRunnable,"Red");
		
		t1.start();
		t2.start();
    }
    
  
}
