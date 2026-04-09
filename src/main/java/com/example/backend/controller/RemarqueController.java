package com.example.backend.controller;
import com.example.backend.model.Remarque;
import com.example.backend.repository.RemarqueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/remarque")@CrossOrigin(origins="http://localhost:4200")public class RemarqueController{private final RemarqueRepository repo;public RemarqueController(RemarqueRepository r){this.repo=r;}@GetMapping("/{id}")public List<Remarque> get(@PathVariable Long id){return repo.findByEtudiantId(id);}@PostMapping public ResponseEntity<Remarque> add(@RequestBody Remarque r){r.setDate(LocalDateTime.now());r.setLu(false);return ResponseEntity.ok(repo.save(r));}@PatchMapping("/{id}/lu")public ResponseEntity<Remarque> lu(@PathVariable Long id){Optional<Remarque> o=repo.findById(id);if(o.isPresent()){Remarque r=o.get();r.setLu(true);repo.save(r);return ResponseEntity.ok(r);}return ResponseEntity.notFound().build();}@DeleteMapping("/{id}")public ResponseEntity<?> del(@PathVariable Long id){if(repo.existsById(id)){repo.deleteById(id);return ResponseEntity.ok().build();}return ResponseEntity.notFound().build();}}