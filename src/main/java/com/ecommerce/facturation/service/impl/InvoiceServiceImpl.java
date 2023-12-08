package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.Enum.InvoiceStatus;
import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dao.InvoiceDao;
import com.ecommerce.facturation.dto.ClientDTO;
import com.ecommerce.facturation.dto.CommandItemDto;
import com.ecommerce.facturation.dto.InvoiceDTO;
import com.ecommerce.facturation.dto.OrderDto;
import com.ecommerce.facturation.exception.InvoiceNotFoundException;
import com.ecommerce.facturation.jms.MessageConsumer;
import com.ecommerce.facturation.mapper.InvoiceMapper;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.ecommerce.facturation.service.facade.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private InvoiceMapper invoiceMapper;

    public void setInvoiceMapper(InvoiceMapper invoiceMapper) {
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public List<InvoiceDTO> getInvoices() {
        return invoiceDao.findAll().stream().map(invoice -> invoiceMapper.fromInvoice(invoice)).collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(Long id) {
        Invoice invoice = invoiceDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found with id: " + id));
        return invoiceMapper.fromInvoice(invoice);
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice savedInvoice = invoiceDao.save(invoice);
        return invoiceMapper.fromInvoice(savedInvoice);
    }

    @Autowired
    private JsonMapper jsonMapper;

    public InvoiceDTO save(String payload) {
        OrderDto orderDto = jsonMapper.convertJsonToObject(payload, OrderDto.class);
        ClientDTO clientDTO = jsonMapper.convertJsonToObject(orderDto.client(), ClientDTO.class);
        List<CommandItemDto> commandItemDtos = jsonMapper.convertJsonToObjects(orderDto.commandItemDtos(), CommandItemDto.class);
        InvoiceDTO invoiceDTO = new InvoiceDTO(
                orderDto.reference(),
                LocalDateTime.now().plusDays(30),
                InvoiceStatus.Paid,
                orderDto.totalPay(),
                clientDTO,
                commandItemDtos
        );
        return save(invoiceDTO);
    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoiceDTO) {
        findById(invoiceDTO.invoiceId());
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice updatedInvoice = invoiceDao.save(invoice);
        return invoiceMapper.fromInvoice(updatedInvoice);
    }

    @Override
    public boolean deleteInvoiceById(Long id) {
        InvoiceDTO invoiceDTO = findById(id);
        if (invoiceDTO != null) {
            invoiceDao.deleteById(id);
            return true;
        } else return false;
    }

//    @Autowired
//    private JsonMapper jsonMapper;
//
//    public OrderDto receiveOrder(String jsonMessage) throws JsonProcessingException, ClassNotFoundException {
//        OrderDto orderDto = jsonMapper.convertJsonToObject(jsonMessage, OrderDto.class);
//        return orderDto;
//    }


}
