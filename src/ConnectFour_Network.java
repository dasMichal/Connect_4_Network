import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class ConnectFour_Network extends networkCore {


    public static void main(String[] args) throws IOException, InterruptedException
    {

        Scanner input = new Scanner(System.in);



        // create an array with the size of the needed connect four field
        char[][] array = new char[6][7];

        // fill the complete array with ' ' (equals empty on the field)
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                array[i][j] = ' ';
            }
        }


        String play1 = "Player X";
        String play2 = "Player O";
        String s;
        int player;
        int column;
        int playerID = 0;
        int userChoice = 1;

        boolean networkmode = false;
        boolean networkPlay1 = false;
        boolean networkPlay2 = false;
        boolean inputOnlyDigits;
        boolean reciver;


    /*
        System.out.print("\n");
        System.out.print("------------------CONNECT 4--------------------\n");
        System.out.print("Do you want to play in Online mode?\n");
        System.out.print("-----------------------------------------------\n\n");
    */



        System.out.print("\n");
        System.out.print("------------------CONNECT 4--------------------\n");
        System.out.print("------------CHOOSE THE GAME MODE---------------\n");
        System.out.print("[1] Singleplayer\n");
        System.out.print("[2] Multiplayer\n");
        System.out.print("[3] Exit\n");
        System.out.print("-----------------------------------------------\n\n");




        do
        {
            //System.out.print("[1] = Yes | [2] = No: ");
            System.out.print(">>> ");
            String userChoiceString = input.next();

            // check if the user input only contains digits
            inputOnlyDigits = true;
            for (int i = 0; i < userChoiceString.length(); i++)
            {
                if (!Character.isDigit(userChoiceString.charAt(i)))
                {
                    inputOnlyDigits = false;
                }
            }
            // user input only contains numbers
            if (inputOnlyDigits)
            {
                // convert string to int
                userChoice = Integer.parseInt(userChoiceString);
            }
            // user input not only contains numbers
            if (!inputOnlyDigits)
            {
                System.out.println("Invalid input! Must be a number.");
            }
            // user input is not 1 or 2
            else if (userChoice < 1 || userChoice > 3)
            {
                System.out.println("Invalid input! Enter a number between 1-3.");
                inputOnlyDigits = false;
            }

        } while (!inputOnlyDigits);



        switch (userChoice)
        {
            case 1:
                System.out.print("Singleplayer started\r");
                break;

            case 2:

                String ip_address;
                do
                {
                    InetAddress inetAddress = InetAddress.getLocalHost();

                    System.out.println("Please enter the IP adress of the other player");
                    System.out.println("Your IP Adress is "+inetAddress.getHostAddress());
                    System.out.print(">>> \r");
                    ip_address = input.next();

                    if (isValidInet4Address(ip_address))
                    {
                        System.out.print("The IP address " + ip_address + " is valid");
                        setIp_address(ip_address);
                    } else
                    {
                        System.out.print("The IP address " + ip_address + " isn't valid");
                    }
                }while (!isValidInet4Address(ip_address));

                //TODO: Portabfrage. Der Setter setPort ist schon eingerichtet.


                System.out.println("\n\nAre you playing as Player[1] or Player[2] ?");
                System.out.println(">>> ");
                int eingabe = input.nextInt();
                if (eingabe == 1)
                {
                    networkPlay2= true;
                    System.out.println(play2+" is in Networke Mode");
                }else
                {
                    networkPlay1= true;
                    System.out.println(play1+" is in Networke Mode");
                }
                //TODO: Abfangen von falschen eingaben muss noch gemacht werden

                networkmode = true;

                break;

            case 3:
                System.out.println("Exiting");

                System.exit(0);
                break;
            default:


        }







            // execute loop while no one has won yet

        while (playerWins(array) == 0)
            {
                System.out.print("\n\n------------ Round Nr. " + (playerID + 1) + "------------");
                showField(array);

                column = 0;
                reciver= false;
                inputOnlyDigits = true;
                // this loop executes until the user gives a legal input for a column (1-7)
                do
                {

                    if ((playerID % 2) == 0)
                    {
                        player = 1;
                        System.out.println("\n" + play1 + "'s turn ");

                        if (networkPlay1)
                        {
                            System.out.println("Waiting for network answer");
                            reciver=true;
                            reciverNet();
                            String[] dat = getData();
                            s = dat[1];
                        }else
                        {
                            System.out.print("Input the number of the column [1-7] to insert : ");
                            s = input.next();
                        }




                    } else
                    {
                        player = 2;
                        System.out.println("\n" + play2 + "'s turn ");

                        if (networkPlay2)
                        {
                            System.out.println("Waiting for network answer");
                            reciver=true;
                            reciverNet();
                            String[] dat = getData();
                            s = dat[1];
                        }else
                        {
                            System.out.print("Input the number of the column [1-7] to insert : ");
                            s = input.next();
                        }
                    }


                    // check if the user input only contains digits

                    for (int i = 0; i < s.length(); i++)
                    {
                        if (!Character.isDigit(s.charAt(i)))
                        {
                            inputOnlyDigits = false;
                        }
                    }

                    if (inputOnlyDigits)
                    {
                        // convert string to int
                        column = Integer.parseInt(s);
                    }

                    // user inputs not only numbers
                    if (!inputOnlyDigits)
                    {
                        System.out.println("Invalid input! Must be a number.");
                    }

                    // user gives a invalid column
                    else if (column <= 0 || column > 7)
                    {
                        System.out.println("Invalid input. Column must be between 1-7!");
                    }

                    // user wants to place in a column that is already full
                    else if (columnFull(column - 1, array))
                    {
                        System.out.println("Column is full! Choose another one.");
                        column = 0;
                    }

                } while (column <= 0 || column > 7 || !inputOnlyDigits);

                //System.out.println("Inserting Chip from "+player);
                insertChip(player, column,array);

                if((!reciver) & (networkmode))
                {
                    if (!networkPlay1)
                    {
                        //Transmitts Player 1 move to Player 2 via network
                        transmittNet(s,play1, (byte) 1);
                    }else if (!networkPlay2)
                    {

                        //Transmitts Player 2 move to Player 1 via network
                        transmittNet(s,play2, (byte) 1);
                    }

                }

                playerID++;
            }

            showField(array);

            if (playerWins(array) == 1)
            {
                System.out.println("\n\n--------------");
                System.out.println(play1 + " wins!");
                System.out.println("--------------");
            } else if (playerWins(array) == 2)
            {
                System.out.println("\n\n--------------");
                System.out.println(play2 + " wins!");
                System.out.println("--------------");
            } else if (playerWins(array) == 3)
            {
                System.out.println("\n\n--------------");
                System.out.println("This game is a draw!");
                System.out.println("--------------");
            }

            input.close();
        }


        /** prints the field in a nice looking way */
        private static void showField ( char[][] array){

        System.out.print("\n");
        System.out.println(" 1 2 3 4 5 6 7");

        // iterate trough the array and print each element (' ', 'X' or 'O')
        // separated with "|"
        for (int i = 0; i < 6; i++)
        {
            System.out.print("|");

            for (int j = 0; j < 7; j++)
            {
                System.out.print(array[i][j]);
                System.out.print("|");
            }
            System.out.print("\n");
        }
    }

        /** check if a column is already full */
        private static boolean columnFull (int column, char[][] array){
        return array[0][column] != ' ';
    }

        /** insert a chip into the field
         * column is the column of the playerfield, not the array (so 1-7)*/
        private static void insertChip (int player, int column, char[][] array){
        column -= 1;
        for (int i = 5; i >= 0; i--)
        {
            if (array[i][column] == ' ')
            {
                if (player == 1)
                {
                    array[i][column] = 'X';
                    break;
                } else
                {
                    array[i][column] = 'O';
                    break;
                }
            }
        }
    }


        /**
         * this function checks if one player has won yet
         * return 0: no one has won yet
         * return 1: player1 wins
         * return 2: player2 wins
         * return 3: draw
         */
        private static int playerWins ( char[][] array){

        // check horizontally -------------------------------------
        int countX;
        int countO;

        for (int i = 0; i < 6; i++)
        {
            // reset counters after each horizontal line
            countX = 0;
            countO = 0;
            for (int j = 0; j < 7; j++)
            {
                // 'X'
                if (array[i][j] == 'X')
                {
                    countX++;
                    countO = 0;
                }
                // 'O'
                else if (array[i][j] == 'O')
                {
                    countO++;
                    countX = 0;
                }
                // ' '
                else
                {
                    countX = 0;
                    countO = 0;
                }

                // check if char occurred 4 times in a row
                if (countX == 4)
                {
                    return 1;
                } else if (countO == 4)
                {
                    return 2;
                }
            }
        }

        // check vertically -------------------------------------
        for (int i = 0; i < 7; i++)
        {
            // reset counters after each vertical line
            countX = 0;
            countO = 0;
            for (int j = 0; j < 6; j++)
            {
                // 'X'
                if (array[j][i] == 'X')
                {
                    countX++;
                    countO = 0;
                }
                // 'O'
                else if (array[j][i] == 'O')
                {
                    countO++;
                    countX = 0;
                }
                // ' '
                else
                {
                    countX = 0;
                    countO = 0;
                }

                // check if char occurred 4 times in a row
                if (countX == 4)
                {
                    return 1;
                } else if (countO == 4)
                {
                    return 2;
                }
            }
        }

        // check diagonally -------------------------------------
        countX = 0;
        countO = 0;

        // rows from left bottom to right top
            //TODO: Warscheinlich wir hier doppelt die Array abgefragt. Siehe 426 und 430
        for (int j = 3; j <= 5; j++)
        {
            for (int i = 0, x = j; i <= j; i++, x--)
            {
                if (array[i][x] == 'X')
                {
                    countX++;
                    countO = 0;
                } else if (array[i][x] == 'X')
                {
                    countO++;
                    countX = 0;
                } else
                {
                    countX = 0;
                    countO = 0;
                }

                // check if char occurred 4 times in a row
                if (countX == 4)
                {
                    return 1;
                } else if (countO == 4)
                {
                    return 2;
                }
            }
            countX = 0;
            countO = 0;
        }
        for (int i = 0; i <= 2; i++)
        {
            for (int x = i, j = 6; x <= 5; x++, j--)
            {
                if (array[x][j] == 'X')
                {
                    countX++;
                    countO = 0;
                } else if (array[x][j] == 'X')
                {
                    countO++;
                    countX = 0;
                } else
                {
                    countX = 0;
                    countO = 0;
                }

                // check if char occurred 4 times in a row
                if (countX == 4)
                {
                    return 1;
                } else if (countO == 4)
                {
                    return 2;
                }
            }
            countX = 0;
            countO = 0;
        }

        // rows from top left to right bottom
        for (int j = 3; j <= 5; j++)
        {
            for (int i = 0, x = 5 - j; i <= j; i++, x++)
            {
                if (array[x][i] == 'X')
                {
                    countX++;
                    countO = 0;
                } else if (array[x][i] == 'X')
                {
                    countO++;
                    countX = 0;
                } else
                {
                    countX = 0;
                    countO = 0;
                }

                // check if char occurred 4 times in a row
                if (countX == 4)
                {
                    return 1;
                } else if (countO == 4)
                {
                    return 2;
                }
            }
            countX = 0;
            countO = 0;
        }
        for (int i = 1; i <= 3; i++)
        {
            for (int x = i, j = 0; x <= 6; x++, j++)
            {
                if (array[j][x] == 'X')
                {
                    countX++;
                    countO = 0;
                } else if (array[j][x] == 'X')
                {
                    countO++;
                    countX = 0;
                } else
                {
                    countX = 0;
                    countO = 0;
                }

                // check if char occurred 4 times in a row
                if (countX == 4)
                {
                    return 1;
                } else if (countO == 4)
                {
                    return 2;
                }
            }
            countX = 0;
            countO = 0;
        }

        // check for draw -------------------------------------
        boolean everythingFilled = true;
        // check if every cell is filled
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (array[i][j] == ' ')
                {
                    everythingFilled = false;
                    break;
                }
            }
        }

        if (everythingFilled)
        {
            return 3;
        }

        // no win has been found
        return 0;


    }


}
