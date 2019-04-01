package com.woodfish.security;

import org.junit.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class InterviewTest {

    @Test
    public void test(){
        Map <Character,Integer> characterIntegerMap = new HashMap<>();
        String str = "abbccaca";

        for(int i=0;i<str.length();i++){
            if(characterIntegerMap.containsKey(str.charAt(i))){
                characterIntegerMap.put(str.charAt(i),characterIntegerMap.get(str.charAt(i))+1);
                continue;
            }
            characterIntegerMap.put(str.charAt(i), 1);
        }
        for (Map.Entry<Character,Integer> entry:
                characterIntegerMap.entrySet()) {
            System.out.println("key = "+entry.getKey()+", value = "+entry.getValue());
        }

        System.out.println("运行到这里");
        Map<Character,Integer> treeMap = new TreeMap<>(new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return characterIntegerMap.get(o1).compareTo(characterIntegerMap.get(o2)) <= 0 ? 1 : -1;
            }
        });
        treeMap.putAll(characterIntegerMap);
        System. out.println("运行到这里");
        for (Map.Entry<Character,Integer> entry:
                treeMap.entrySet()) {
            System.out.println("key = "+entry.getKey()+", value = "+entry.getValue());
        }
        System.out.println("所以第一个元素 是这玩意吗");
        System.out.println(treeMap.keySet().iterator().next());
    }


}
