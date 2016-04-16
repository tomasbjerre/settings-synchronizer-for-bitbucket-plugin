package se.bjurr.ssfb.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static se.bjurr.ssfb.settings.SsfbSettings.ssfbSettingsBuilder;
import static se.bjurr.ssfb.settings.SyncEvery.HOURLY;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import se.bjurr.ssfb.admin.dto.SsfbGlobalSyncSettingsDTO;
import se.bjurr.ssfb.service.ScheduleService;
import se.bjurr.ssfb.service.SettingsService;
import se.bjurr.ssfb.settings.SsfbSettings;

public class GlobalAdminServletTest {
 @Mock
 private ScheduleService scheduleService;
 @Mock
 private SettingsService settingsService;
 @Captor
 private ArgumentCaptor<SsfbSettings> updatedSettingsCaptor;
 private GlobalAdminServlet sut;

 @Before
 public void before() {
  initMocks(this);
  sut = new GlobalAdminServlet(scheduleService, settingsService);
 }

 @Test
 public void testThatEmptySettingsCanBeReturned() throws Exception {
  SsfbSettings ssfbSettings = ssfbSettingsBuilder()//
    .build();
  when(settingsService.getSsfbSettings())//
    .thenReturn(ssfbSettings);

  Response actual = sut.get();

  assertThat(actual.getStatus())//
    .isEqualTo(200);
 }

 @Test
 public void testThatSettingsCanBeReturned() throws Exception {
  SsfbSettings ssfbSettings = ssfbSettingsBuilder()//
    .setStartTime("01:01")//
    .setSyncEvery(HOURLY)//
    .build();
  when(settingsService.getSsfbSettings())//
    .thenReturn(ssfbSettings);

  Response actual = sut.get();

  assertThat(actual.getStatus())//
    .isEqualTo(200);
  SsfbGlobalSyncSettingsDTO entity = (SsfbGlobalSyncSettingsDTO) actual.getEntity();
  assertThat(entity.getStartTime())//
    .isEqualTo("01:01");
  assertThat(entity.getSyncEvery())//
    .isEqualTo(HOURLY.name());
 }

 @Test
 public void testThatSettingsCanBeSaved() throws Exception {
  SsfbSettings previousSettings = ssfbSettingsBuilder()//
    .setStartTime("01:01")//
    .setSyncEvery(HOURLY)//
    .build();
  when(settingsService.getSsfbSettings())//
    .thenReturn(previousSettings);

  SsfbGlobalSyncSettingsDTO ssfbRepoSettingsDTO = new SsfbGlobalSyncSettingsDTO();
  ssfbRepoSettingsDTO.setStartTime("02:02");
  ssfbRepoSettingsDTO.setSyncEvery(HOURLY.name());

  sut.post(ssfbRepoSettingsDTO);

  verify(scheduleService)//
    .setup("02:02", HOURLY);
  verify(settingsService)//
    .setSsfbSettings(updatedSettingsCaptor.capture());
  assertThat(updatedSettingsCaptor.getValue().getStartTime())//
    .isEqualTo("02:02");
  assertThat(updatedSettingsCaptor.getValue().getSyncEvery())//
    .isEqualTo(HOURLY);
 }
}
