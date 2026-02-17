package com.api.demo.services;
import com.api.demo.dto.pagamento.PagamentoCreateDTO;
import com.api.demo.dto.pagamento.PagamentoResponseDTO;
import com.api.demo.dto.pagamento.PagamentoUpdateDTO;
import com.api.demo.mapper.PagamentoMapper;
import com.api.demo.model.Aula;
import com.api.demo.model.Pagamento;
import com.api.demo.repository.AulaRepository;
import com.api.demo.repository.PagamentoRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PagamentoServices {

    private final PagamentoRepository pagamentoRepository;
    private final AulaRepository aulaRepository;

    private final PagamentoMapper pagamentoMapper;

    public PagamentoServices(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper, AulaRepository aulaRepository){
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoMapper = pagamentoMapper;
        this.aulaRepository = aulaRepository;
    }

    public List<PagamentoResponseDTO> findAll(){
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        List<PagamentoResponseDTO> pagamentoResponseDTOList = new ArrayList<>();

        for(Pagamento pagamento : pagamentos){
            pagamentoResponseDTOList.add(pagamentoMapper.toDto(pagamento));
        }

        return pagamentoResponseDTOList;
    }

    public PagamentoResponseDTO updateStatus(String paymentIntentId, String novoStatus) {
        Pagamento pagamento = pagamentoRepository.findByPaymentIntentId(paymentIntentId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.setStatus(novoStatus);
        PagamentoResponseDTO pagamentoResponseDTO = pagamentoMapper.toDto(pagamento);
        return pagamentoMapper.toDto(pagamentoRepository.save(pagamento));
    }

    public PagamentoResponseDTO findById(Long id){
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Pagamento não encontrada");
        });

        PagamentoResponseDTO pagamentoResponseDTO = pagamentoMapper.toDto(pagamento);

        return pagamentoResponseDTO;
    }

    public void delete(long id){
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Pagamento não encontrada");
        });

        pagamentoRepository.delete(pagamento);
    }

    public PagamentoResponseDTO save(PagamentoCreateDTO dto){
        Aula aula = aulaRepository.findById(dto.getIdAula())
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        Pagamento pagamento = pagamentoMapper.toEntity(dto);
        pagamento.setAula(aula);
        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
        PagamentoResponseDTO pagamentoResponseDTO = pagamentoMapper.toDto(pagamentoSalvo);

        return pagamentoResponseDTO;
    }

    public boolean activeAccount(String accountId) throws StripeException {

        System.out.println(accountId);
        Account account = Account.retrieve(accountId);
        System.out.println(account.getChargesEnabled());
        System.out.println(account.getPayoutsEnabled());

        return account.getChargesEnabled() && account.getPayoutsEnabled();
    }

    public PagamentoResponseDTO update(Long id, PagamentoUpdateDTO dto){
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        pagamentoMapper.updateEntityFromDTO(dto, pagamento);

        Aula aula = aulaRepository.findById(dto.getIdAula())
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));
        pagamento.setAula(aula);


        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toDto(pagamentoSalvo);
    }

}
