package baseball.gameProcess;

import baseball.GameRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InProcess {
    private int number;
    private List<Integer> my;
    private List<Integer> computer;
    private boolean isCorrect;

    public InProcess(GameRole gameRole, List<Integer> computer) {
        initVariables(computer);

        printInputNumberPhrase();
        inputNumber();
        checkInputNumber(gameRole);

        judgeInputNumber(gameRole, computer);
    }

    public boolean isNumberCorrect() {
        return isCorrect;
    }

    private void initVariables(List<Integer> computer) {
        number = 0;
        my = new ArrayList<>();
        this.computer = computer;
        isCorrect = false;
    }

    private void printInputNumberPhrase() {
        System.out.print(InNotice.inputNumberPhrase);
    }

    private void inputNumber() {
        Scanner sc = new Scanner(System.in);

        try {
            number = sc.nextInt();
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private void checkInputNumber(GameRole gameRole) {
        checkNumberThree(gameRole);
        checkNumberOneToNineDifferent(gameRole);
        numberToList(gameRole);
    }

    private void checkNumberThree(GameRole gameRole) {
        if (String.valueOf(number).length() != gameRole.GAME_NUMBER_CNT) {
            throw new IllegalArgumentException();
        }
    }

    private void checkNumberOneToNineDifferent(GameRole gameRole) {
        boolean[] exist = new boolean[gameRole.GAME_NUMBER_RANGE_UNDER+1];
        int share = number;
        int remain;

        for (int i = 0; i < gameRole.GAME_NUMBER_CNT; i++) {
            remain = share % 10;
            share /= 10;

            if (remain == 0 || exist[remain]) {
                throw new IllegalArgumentException();
            }
            exist[remain] = true;
        }
    }

    private void numberToList(GameRole gameRole) {
        my = new ArrayList<>();

        String num = String.valueOf(number);
        for (int i = 0; i < gameRole.GAME_NUMBER_CNT; i++) {
            my.add(num.charAt(i)-'0');
        }
    }

    private void judgeInputNumber(GameRole gameRole, List<Integer> computer) {
        int ball = 0;
        int strike = 0;

        compareInputNumber(gameRole, computer, ball, strike);
        printCollectNumber(ball, strike);
        checkAllStrike(gameRole, strike);
    }

    public void checkAllStrike(GameRole gameRole, int strike) {
        if (strike == gameRole.GAME_NUMBER_CNT) {
            isCorrect = true;
            printFinishPhrase(gameRole);
        }
    }

    public void printFinishPhrase(GameRole gameRole) {
        System.out.println(gameRole.GAME_NUMBER_CNT+InNotice.finishPhrase);
    }

    private void printCollectNumber(int ball, int strike) {
        if (ball == 0 && strike == 0) printNothing();
        else if (ball == 0 && strike > 0) printStrike(strike);
        else if (ball > 0 && strike == 0) printBall(ball);
        else printBallAndStrike(ball, strike);
    }

    private void printBallAndStrike(int ball, int strike) {
        System.out.println(ball+""+InNotice.ball+" "+strike+""+InNotice.strike);
    }

    private void printBall(int ball) {
        System.out.println(ball+""+InNotice.ball);
    }

    private void printStrike(int strike) {
        System.out.println(strike+""+InNotice.strike);
    }

    private void printNothing() {
        System.out.println(InNotice.nothing);
    }

    private void compareInputNumber(GameRole gameRole, List<Integer> computer, int ball, int strike) {
        for (int i = 0; i < gameRole.GAME_NUMBER_CNT; i++) {
            if (my.get(i) == computer.get(i)) {
                strike++;
            }
            else {
                if (computer.contains(my.get(i))) ball++;
            }
        }
    }
}
