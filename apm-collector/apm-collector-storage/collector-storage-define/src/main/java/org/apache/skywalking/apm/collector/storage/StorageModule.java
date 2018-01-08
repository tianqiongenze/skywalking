/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.collector.storage;

import java.util.ArrayList;
import java.util.List;
import org.apache.skywalking.apm.collector.core.module.Module;
import org.apache.skywalking.apm.collector.storage.base.dao.IBatchDAO;
import org.apache.skywalking.apm.collector.storage.dao.IApplicationAlarmListPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IApplicationAlarmPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IApplicationCacheDAO;
import org.apache.skywalking.apm.collector.storage.dao.IApplicationComponentUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.IApplicationMappingUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.IApplicationReferenceAlarmListPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IApplicationReferenceAlarmPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IApplicationReferenceMetricUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.ICpuMetricUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.IGCMetricUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.IGlobalTracePersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IGlobalTraceUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.IInstanceAlarmListPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IInstanceAlarmPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IInstanceCacheDAO;
import org.apache.skywalking.apm.collector.storage.dao.IInstanceHeartBeatPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IInstanceMetricUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.IInstanceReferenceAlarmListPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IInstanceReferenceAlarmPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IInstanceUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.IMemoryMetricUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.IMemoryPoolMetricUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.INetworkAddressCacheDAO;
import org.apache.skywalking.apm.collector.storage.dao.ISegmentCostPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.ISegmentCostUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.ISegmentPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.ISegmentUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.IServiceAlarmListPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IServiceAlarmPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IServiceNameCacheDAO;
import org.apache.skywalking.apm.collector.storage.dao.IServiceReferenceAlarmListPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IServiceReferenceAlarmPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.IServiceReferenceUIDAO;
import org.apache.skywalking.apm.collector.storage.dao.acp.IApplicationComponentDayPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.acp.IApplicationComponentHourPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.acp.IApplicationComponentMinutePersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.acp.IApplicationComponentMonthPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.amp.IApplicationMinuteMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.ampp.IApplicationMappingDayPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.ampp.IApplicationMappingHourPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.ampp.IApplicationMappingMinutePersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.ampp.IApplicationMappingMonthPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.armp.IApplicationReferenceMinuteMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.cpump.ICpuSecondMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.gcmp.IGCSecondMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.imp.IInstanceMinuteMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.impp.IInstanceMappingDayPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.impp.IInstanceMappingHourPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.impp.IInstanceMappingMinutePersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.impp.IInstanceMappingMonthPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.irmp.IInstanceReferenceMinuteMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.memorymp.IMemorySecondMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.mpoolmp.IMemoryPoolSecondMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.register.IApplicationRegisterDAO;
import org.apache.skywalking.apm.collector.storage.dao.register.IInstanceRegisterDAO;
import org.apache.skywalking.apm.collector.storage.dao.register.INetworkAddressRegisterDAO;
import org.apache.skywalking.apm.collector.storage.dao.register.IServiceNameRegisterDAO;
import org.apache.skywalking.apm.collector.storage.dao.smp.IServiceDayMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.smp.IServiceHourMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.smp.IServiceMinuteMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.smp.IServiceMonthMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.srmp.IServiceReferenceDayMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.srmp.IServiceReferenceHourMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.srmp.IServiceReferenceMinuteMetricPersistenceDAO;
import org.apache.skywalking.apm.collector.storage.dao.srmp.IServiceReferenceMonthMetricPersistenceDAO;

/**
 * @author peng-yongsheng
 */
public class StorageModule extends Module {

    public static final String NAME = "storage";

    @Override public String name() {
        return NAME;
    }

    @Override public Class[] services() {
        List<Class> classes = new ArrayList<>();
        classes.add(IBatchDAO.class);

        addCacheDAO(classes);
        addRegisterDAO(classes);
        addPersistenceDAO(classes);
        addUiDAO(classes);
        addAlarmDAO(classes);

        return classes.toArray(new Class[] {});
    }

    private void addCacheDAO(List<Class> classes) {
        classes.add(IApplicationCacheDAO.class);
        classes.add(IInstanceCacheDAO.class);
        classes.add(IServiceNameCacheDAO.class);
        classes.add(INetworkAddressCacheDAO.class);
    }

    private void addRegisterDAO(List<Class> classes) {
        classes.add(IApplicationRegisterDAO.class);
        classes.add(IInstanceRegisterDAO.class);
        classes.add(IServiceNameRegisterDAO.class);
        classes.add(INetworkAddressRegisterDAO.class);
    }

    private void addPersistenceDAO(List<Class> classes) {
        classes.add(ICpuSecondMetricPersistenceDAO.class);
        classes.add(IGCSecondMetricPersistenceDAO.class);
        classes.add(IMemorySecondMetricPersistenceDAO.class);
        classes.add(IMemoryPoolSecondMetricPersistenceDAO.class);

        classes.add(IApplicationComponentMinutePersistenceDAO.class);
        classes.add(IApplicationComponentHourPersistenceDAO.class);
        classes.add(IApplicationComponentDayPersistenceDAO.class);
        classes.add(IApplicationComponentMonthPersistenceDAO.class);

        classes.add(IApplicationMappingMinutePersistenceDAO.class);
        classes.add(IApplicationMappingHourPersistenceDAO.class);
        classes.add(IApplicationMappingDayPersistenceDAO.class);
        classes.add(IApplicationMappingMonthPersistenceDAO.class);

        classes.add(IInstanceMappingMinutePersistenceDAO.class);
        classes.add(IInstanceMappingHourPersistenceDAO.class);
        classes.add(IInstanceMappingDayPersistenceDAO.class);
        classes.add(IInstanceMappingMonthPersistenceDAO.class);

        classes.add(IGlobalTracePersistenceDAO.class);
        classes.add(IApplicationMinuteMetricPersistenceDAO.class);
        classes.add(IApplicationReferenceMinuteMetricPersistenceDAO.class);
        classes.add(ISegmentCostPersistenceDAO.class);
        classes.add(ISegmentPersistenceDAO.class);

        classes.add(IServiceMinuteMetricPersistenceDAO.class);
        classes.add(IServiceHourMetricPersistenceDAO.class);
        classes.add(IServiceDayMetricPersistenceDAO.class);
        classes.add(IServiceMonthMetricPersistenceDAO.class);

        classes.add(IServiceReferenceMinuteMetricPersistenceDAO.class);
        classes.add(IServiceReferenceHourMetricPersistenceDAO.class);
        classes.add(IServiceReferenceDayMetricPersistenceDAO.class);
        classes.add(IServiceReferenceMonthMetricPersistenceDAO.class);

        classes.add(IInstanceMinuteMetricPersistenceDAO.class);
        classes.add(IInstanceReferenceMinuteMetricPersistenceDAO.class);
        classes.add(IInstanceHeartBeatPersistenceDAO.class);
    }

    private void addUiDAO(List<Class> classes) {
        classes.add(IInstanceUIDAO.class);

        classes.add(ICpuMetricUIDAO.class);
        classes.add(IGCMetricUIDAO.class);
        classes.add(IMemoryMetricUIDAO.class);
        classes.add(IMemoryPoolMetricUIDAO.class);

        classes.add(IGlobalTraceUIDAO.class);
        classes.add(IInstanceMetricUIDAO.class);
        classes.add(IApplicationComponentUIDAO.class);
        classes.add(IApplicationMappingUIDAO.class);
        classes.add(IApplicationReferenceMetricUIDAO.class);
        classes.add(ISegmentCostUIDAO.class);
        classes.add(ISegmentUIDAO.class);
        classes.add(IServiceReferenceUIDAO.class);
    }

    private void addAlarmDAO(List<Class> classes) {
        classes.add(IServiceReferenceAlarmPersistenceDAO.class);
        classes.add(IServiceReferenceAlarmListPersistenceDAO.class);
        classes.add(IInstanceReferenceAlarmPersistenceDAO.class);
        classes.add(IInstanceReferenceAlarmListPersistenceDAO.class);
        classes.add(IApplicationReferenceAlarmPersistenceDAO.class);
        classes.add(IApplicationReferenceAlarmListPersistenceDAO.class);

        classes.add(IServiceAlarmPersistenceDAO.class);
        classes.add(IServiceAlarmListPersistenceDAO.class);
        classes.add(IInstanceAlarmPersistenceDAO.class);
        classes.add(IInstanceAlarmListPersistenceDAO.class);
        classes.add(IApplicationAlarmPersistenceDAO.class);
        classes.add(IApplicationAlarmListPersistenceDAO.class);
    }
}
