package com.study.patterns.prototypePattern;

import java.util.Random;

/**
 * 原型模式 银行电子广告
 * 1.有什么弊端 2.单线程，发送 600万封需要多长时间 3.改用多线程
 * @author Administrator
 *
 */
public class CopyOfClient {

	public static int MAX_COUNT = 500;

    public static void main(String[] args) {
        /* 发送邮件 */
        final Mail mail = new Mail(new MailTemp());
        mail.setTail("xxx银行的所有版权");

        for (int i = 0; i < MAX_COUNT; i++) {
            mail.setSub(getRandString(5) + " 先生（女士） ");
            mail.setReceiver(getRandString(5) + "@" + getRandString(8) + ".com");
            sendMail(mail);

        }
    }

    public static void sendMail(Mail mail) {
    	try {
			Thread.sleep(100l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println("标题： " + mail.getSub() + "\t收件人"
                + mail.getReceiver() + "\t....发送成功！ ");
    }

    public static String getRandString(int maxLength) {
        String source = "abcdefghijklmnopqrskuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random rand = new Random();
        for (int i = 0; i < maxLength; i++) {
            sb.append(source.charAt(rand.nextInt(source.length())));
        }
        return sb.toString();
    }
}
