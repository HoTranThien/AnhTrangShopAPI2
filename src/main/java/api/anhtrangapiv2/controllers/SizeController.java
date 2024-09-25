package api.anhtrangapiv2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.anhtrangapiv2.dtos.SizeDTO;
import api.anhtrangapiv2.models.Size;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.responses.SizeResponse;
import api.anhtrangapiv2.service.size.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="${api.prefix}/size")
public class SizeController {

    private final SizeService sizeService;

    @GetMapping(path="/getall")
    public ResponseEntity<ResponseToClient> getall(){
        List<SizeResponse> sizes = sizeService.getAllSizes();
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(sizes)
        .build());
    }

    @PostMapping(path="/create")
    public  ResponseEntity<ResponseToClient> create(@Valid @RequestBody SizeDTO size){
        Size newsize = sizeService.createSize(size);
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(newsize)
        .build());
    }
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ResponseToClient> update(@PathVariable int id,
    @Valid @RequestBody SizeDTO size){
        Size newsize = sizeService.updateSize(id, size);
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(newsize)
        .build());
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<ResponseToClient> delete(@PathVariable int id) throws Exception{
        sizeService.deleteSize(id);
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(null)
        .build());
    }
}
