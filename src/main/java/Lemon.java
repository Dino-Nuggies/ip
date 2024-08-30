import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class Lemon {
    /*** Messages ***/
    String logoMsg = "____________________________________________________________\n"
            + " Hello! I'm Lemon\n"
            + " What can I do for you?\n";
    String endMsg = " Bye. Hope to see you again soon!\n"
            + "____________________________________________________________\n";
    String barMsg = "____________________________________________________________";
    String emptyListMsg = " Sowwy, theres currently no tasks in your list.\n Ill be happy to add some for you OwO!";
    String listMsg = " Here are the tasks in your list:";
    String markMsg = " Nice! I've marked this task as done:";
    String unmarkMsg = " OK, I've marked this task as not done yet:";
    String addTaskMsg = " Got it. I've added this task:";
    String deleteTaskMsg = " Noted. I've removed this task:";

    /*** Initialising ***/
    ArrayList<Task> tasks = new ArrayList<>();
    int numTasks = 0;

    enum Commands {
        BYE,
        LIST,
        MARK,
        UNMARK,
        TODO,
        EVENT,
        DEADLINE,
        DELETE
    }
    
    public static void main(String[] args) {
        Lemon lemon = new Lemon();
        Scanner scan = new Scanner(System.in);
        boolean run = true;

        /** Program starts here **/
        System.out.print(lemon.logoMsg);

        while (run) {
            System.out.println(lemon.barMsg);

            String input = scan.next().toUpperCase();
            System.out.println(lemon.barMsg);
            try {
                Commands command = Commands.valueOf(input);
                switch (command) {
                case BYE:
                    run = false;
                    break;
                case LIST:
                    if (lemon.numTasks == 0) System.out.println(lemon.emptyListMsg);
                    else {
                        System.out.println(lemon.listMsg);
                        lemon.printList();
                    }
                    break;
                case MARK: {
                    int next = scan.nextInt();
                    if (next > lemon.numTasks || next <= 0) {
                        throw new InvalidCommandException(" OOPS!!! Please select a valid task");
                    }

                    System.out.println(lemon.markMsg);
                    lemon.tasks.get(next - 1).markDone();

                    System.out.println("   " + lemon.tasks.get(next - 1).toString());
                    break;
                }
                case UNMARK: {
                    int next = scan.nextInt();
                    if (next > lemon.numTasks || next <= 0) {
                        throw new InvalidCommandException(" OOPS!!! Please select a valid task");
                    }

                    System.out.println(lemon.unmarkMsg);
                    lemon.tasks.get(next - 1).unmarkDone();

                    System.out.println("   " + lemon.tasks.get(next - 1).toString());
                    break;
                }
                case TODO: {
                    String next = scan.nextLine();

                    lemon.addNewTask(new Todo(next));
                    break;
                }
                case DEADLINE: {
                    String[] next = scan.nextLine().split("/by ");

                    lemon.addNewTask(new Deadline(next[0], next[1]));
                    break;
                }
                case EVENT: {
                    String[] next = scan.nextLine().split("/from ");

                    lemon.addNewTask(new Event(next[0], next[1]));
                }
                case DELETE: {
                    int next = scan.nextInt();
                    if (next > lemon.numTasks || next <= 0) {
                        throw new InvalidCommandException(" OOPS!!! Please select a valid task");
                    }

                    lemon.deleteTask(next);
                }
                }
            } catch (InvalidCommandException | DescriptionException e) {
                System.out.println(e.getMessage());
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(" OOPS!!! I cant understand your mark/unmark, please select using numbers xd");
                scan.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(" OOPS!!! I'm sowwy, but I don't know what that means :-(\n\n" +
                        " I can help you add tasks with \"todo\", \"deadline\", \"event\"\n" +
                        " I can also keep track of all your tasks with \"list\"\n" +
                        " If you wanna update certain tasks, use \"mark\" or \"unmark\" and then its number");
                scan.nextLine();
            }
        }

        System.out.println(lemon.endMsg);
    }

    /*** Functions ***/
    private void printList() {
        for (int i = 0; i < numTasks; i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i).toString());
        }
    }

    private void addNewTask(Task t) throws DescriptionException {
        if (t.description.isEmpty() || t.description.equals(" "))
            throw new DescriptionException(" OOPS!!! The description of a " + t.type + " cannot be empty");
        System.out.println(addTaskMsg);

        tasks.add(t);
        numTasks++;

        System.out.println("   " + t.toString());
        System.out.println(" Now you have " + numTasks + " tasks in the list.");
    }

    private void deleteTask(int index) {
        Task t = tasks.remove(index - 1);
        numTasks--;

        System.out.println(deleteTaskMsg);
        System.out.println("   " + t.toString());
        System.out.println(" Now you have " + numTasks + " tasks in the list.");
    }
}
