

@FeignClient(name = "usuario-service", url = "http://localhost:8082/api/usuario")

public interface veterinarioClient {

    @GetMapping("/email/{email}")
    UsuarioDTO findByEmail(@PathVariable String email);

}
