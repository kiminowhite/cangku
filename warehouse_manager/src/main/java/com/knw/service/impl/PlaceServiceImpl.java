package com.knw.service.impl;

import com.knw.entity.Place;
import com.knw.mapper.PlaceMapper;
import com.knw.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-11 08:59
 */
@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    PlaceMapper placeMapper;
    @Override
    public List<Place> getAllPlaces() {
        return placeMapper.findAllPlaces();
    }
}
