package com.gupaoedu.pattern.template.jdbc;

import com.gupaoedu.pattern.template.jdbc.dao.MemeberDao;

import java.util.List;

public class MemberDaoTest {

    public static void main(String[] args) {
        MemeberDao memeberDao = new MemeberDao(null);
        List<?> result = memeberDao.selectAll();
        System.out.println(result);
    }

}
