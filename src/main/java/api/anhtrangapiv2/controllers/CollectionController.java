package api.anhtrangapiv2.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import api.anhtrangapiv2.dtos.CollectionDTO;
import api.anhtrangapiv2.models.Collection;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.S3Storage.S3StorageService;
import api.anhtrangapiv2.service.collection.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/collection")
@RequiredArgsConstructor
public class CollectionController {
    
    @Autowired
    private S3StorageService s3StorageService;

    @Autowired final CollectionService collectionService;

    
    @GetMapping(path = "/getall")
    public ResponseEntity<Object> getall(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(collectionService.getAllCollection())
        .build());
    }
    @PostMapping(path = "/create")
    public ResponseEntity<Object> create(@ModelAttribute("collection") @Valid CollectionDTO collectionDTO,
    @RequestParam("file") @Valid MultipartFile file) throws RuntimeException{
        Collection result = collectionService.createCollection(file, collectionDTO);
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(result)
        .build());
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(collectionService.deleteCollection(id)).build());
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Object> upadte(@PathVariable int id,
    @ModelAttribute CollectionDTO co, @RequestParam(required = false) MultipartFile file) throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(collectionService.updateCollection(id, co, file)).build());
    }
}
