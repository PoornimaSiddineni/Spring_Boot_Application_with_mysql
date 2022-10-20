package com.example.sql2.service;

import com.example.sql2.entity.Substring;
import com.example.sql2.repository.LcsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class LcsService {

    private final LcsRepository lcsRepo;

    public LcsService(LcsRepository lcsRepository) {
        this.lcsRepo = lcsRepository;
    }

    public static List<String> longestCommonSubstrings(List<String> stringList)
    {
        int n = stringList.size();
        String s = stringList.get(0);
        int len = s.length();

        int max = Integer.MIN_VALUE;
        ArrayList<String> result = null;

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j <= len; j++) {


                String cString = s.substring(i, j);
                int sub_len = cString.length();

                int k ;
                for (k = 1; k < n; k++){
                    if (!stringList.get(k).contains(cString))
                        break;
                }

                if (k == n && max < sub_len){
                    max =  sub_len;
                    result = new ArrayList<>();
                    result.add(cString);
                }
                else if(k == n && max == sub_len)
                {
                    result.add(cString);
                }
            }
        }

        return result;

    }


    public Substring to_Create(List<String> list1)
    {
        Substring sub = new Substring();
        List<String> r;
        sub.request = list1;
        r = longestCommonSubstrings(list1);
        sub.response = r;
        sub.count = r.size();
        lcsRepo.save(sub);
        return sub;

    }

    public Optional<List<Substring>> getAll()
    {
        //return ok(this.lcsRepo.findAll());
        Optional<List<Substring>> list = Optional.of((lcsRepo.findAll()));
        return list;

    }

    public Optional<Substring> getById(@PathVariable String id) {
        Optional<Substring> myList = (lcsRepo.findById(id));

        return myList;

    }


}
