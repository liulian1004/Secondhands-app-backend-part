/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Shuyu Zhou, Yi Ji
 * Email: szhou12@u.rochester.edu, yuki.yi.ji@gmail.com
 */

package secondHandMarket.service;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import secondHandMarket.repository.RequestRepository;
import org.springframework.stereotype.Service;
import secondHandMarket.model.Request;
import secondHandMarket.exception.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    @Override
    public Request getRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Request not found")
        );
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void delete(Request request) {
        requestRepository.delete(request);
    }
}

