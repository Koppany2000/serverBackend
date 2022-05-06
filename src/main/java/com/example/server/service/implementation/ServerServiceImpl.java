package com.example.server.service.implementation;

import com.example.server.enumeration.Status;
import com.example.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.server.model.Server;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.server.repo.ServerRepo;

import java.io.IOException;

import java.net.InetAddress;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server:{}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }



    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP:{}",ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = null;
        address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP:Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}",id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server:{}",server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server server by id: {}",id);
        serverRepo.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImageUrl() {
        return "";
    }
}
