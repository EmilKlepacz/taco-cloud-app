package sia.tacocloud.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.model.Taco;
import sia.tacocloud.service.TacoService;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos",
        produces = "application/json")
//accepts cross-origin requests from the front-end running at http://tacocloud:8080.
//for test app requests...
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoRestController {
    private final TacoService tacoService;

    public TacoRestController(TacoService tacoService) {
        this.tacoService = tacoService;
    }

    @GetMapping(params = "recent")
    public Page<Taco> recentTacos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return tacoService.lastAddedTacos(pageRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> getTaco(@PathVariable Long id) {
        Optional<Taco> taco = tacoService.getTacoById(id);
        if (taco.isPresent()) {
            return new ResponseEntity<>(taco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco createTaco(@RequestBody Taco taco) {
        return tacoService.createTaco(taco);
    }
}
