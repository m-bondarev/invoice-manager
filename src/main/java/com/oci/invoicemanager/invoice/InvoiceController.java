package com.oci.invoicemanager.invoice;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.oracle.bmc.queue.QueueClient;
import com.oracle.bmc.queue.requests.GetMessagesRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1/invoices", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InvoiceController {

  private final NotificationService notificationService;
  private final QueueClient queueClient;

  @GetMapping
  public String getAll() {
    return "{}";
  }

  @GetMapping(value = "/{id}")
  public String getById(
      @PathVariable final long id
  ) {
    final var messages = queueClient.getMessages(GetMessagesRequest.builder()
        .queueId("ocid1.queue.oc1.eu-frankfurt-1.amaaaaaabas6vyiaodmj6xvsrhossja7lgjggjz6npjb63ejgvaa736xhiva")
        .build());

    return messages.getGetMessages().toString();
  }

  @PostMapping
  public InvoiceDto create(
      @RequestBody final InvoiceDto invoiceDto
  ) {
    return invoiceDto;
  }

  @PostMapping("/publishMessage")
  public String publishMessage(@RequestBody PublishMessage publishMessage) {
    return notificationService.publishMessage(publishMessage).toString();
  }

  public record PublishMessage(String message, String title) {

  }
}
