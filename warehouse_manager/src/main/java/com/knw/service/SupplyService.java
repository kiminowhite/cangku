package com.knw.service;

import com.knw.entity.Role;
import com.knw.entity.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 08:43
 */

public interface SupplyService {

    public List<Supply> getAllSupplys();
}
