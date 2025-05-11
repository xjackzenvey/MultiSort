package me.chisato.multisort;

import java.util.Random;
import java.util.logging.Logger;


public class utils {

    private static Random random = new Random();
    private static Logger logger = Logger.getLogger(utils.class.getName());

    // 生成随机排列的数组
    public static int[] generateRandomArray(int size, int min, int max) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = min + random.nextInt(max - min + 1);
        }
        return array;
    }

    // 生成近似有序的数组
    public static int[] generateAlmostSortedArray(int size, int min, int max) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i + min;
        }
        // 进行少量交换
        for (int i = 0; i < size / 10 + 1; i++) {
            int index1 = random.nextInt(size);
            int index2 = random.nextInt(size);
            int temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
        return array;
    }

    // 生成近似倒序的数组
    public static int[] generateAlmostReversedArray(int size, int min, int max) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i - min;
        }
        // 进行少量交换
        for (int i = 0; i < size / 10 + 1; i++) {
            int index1 = random.nextInt(size);
            int index2 = random.nextInt(size);
            int temp = array[index1];
            array[index1] = array[size - index2 - 1];
            array[size - index2 - 1] = temp;
        }
        return array;
    }

    // 用于控制数组生成
    public static int[] handleArray(String dataDistribution, int dataSize, int min, int max) {

        logger.info("生成数组：" + dataDistribution + "，数据量：" + dataSize);

        if (dataDistribution.equals("随机")) {
            return generateRandomArray(dataSize, min, max);
        } else if (dataDistribution.equals("近似有序")) {
            return generateAlmostSortedArray(dataSize, min, max);
        } else if (dataDistribution.equals("近似倒序")) {
            return generateAlmostReversedArray(dataSize, min, max);
        }else {
            // 默认返回随机
            return generateRandomArray(dataSize, min, max);
        }
    }

}
