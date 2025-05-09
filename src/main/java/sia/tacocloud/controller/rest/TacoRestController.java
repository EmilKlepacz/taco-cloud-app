package sia.tacocloud.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.dto.TacoDTO;
import sia.tacocloud.mapper.TacoMapper;
import sia.tacocloud.model.Taco;
import sia.tacocloud.service.TacoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/tacos",
        produces = "application/json")
//accepts cross-origin requests from the front-end running at http://tacocloud:8080.
//for test app requests...
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoRestController {
    private final TacoService tacoService;
    private final TacoMapper tacoMapper;

    public TacoRestController(TacoService tacoService, TacoMapper tacoMapper) {
        this.tacoService = tacoService;
        this.tacoMapper = tacoMapper;
    }

    @GetMapping(params = "recent")
    public ResponseEntity<Page<TacoDTO>> recentTacos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Taco> tacos = tacoService.lastAddedTacos(pageRequest);

        Page<TacoDTO> tacoDTOS = tacos.map(tacoMapper::toDto);
        return ResponseEntity.ok(tacoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacoDTO> getTaco(@PathVariable Long id) {
        Optional<Taco> tacoOpt = tacoService.getTacoById(id);
        return tacoOpt.map(taco -> new ResponseEntity<>(tacoMapper.toDto(taco), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<TacoDTO> createTaco(@RequestBody TacoDTO tacoDto) {
        Taco taco = tacoMapper.toEntity(tacoDto);
        Taco createdTaco = tacoService.createTaco(taco);

        TacoDTO createdTacoDTO = tacoMapper.toDto(createdTaco);
        return ResponseEntity.ok(createdTacoDTO);
    }

}
