package com.example.server.resource;

import com.example.server.enumeration.Status;
import lombok.RequiredArgsConstructor;
import com.example.server.model.Response;
import com.example.server.model.Server;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.server.service.implementation.ServerServiceImpl;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {

    private final ServerServiceImpl serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("servers",serverService.list(30)))
                        .message("Servers retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server",server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server",serverService.create(server)))
                        .message("Server created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server",serverService.get(id)))
                        .message("Server retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("deleted",serverService.delete(id)))
                        .message("Server deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
