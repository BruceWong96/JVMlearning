package com.jvm.test;

class Solution {

    public static double myPow(double x, int n) {
        double result = 1;

        if(n>0){
            while(n>0){
                result *= x;
                n--;
            }
            return result;
        }
        else if(n==0){
            return 1;
        }else {
            n = 0-n;
            while (n>0){
                result *= x;
                n--;
            }
            return 1/result;
        }

    }

    public static void main(String[] args) {
        System.out.println(myPow(2.00000, -2));
    }

}

