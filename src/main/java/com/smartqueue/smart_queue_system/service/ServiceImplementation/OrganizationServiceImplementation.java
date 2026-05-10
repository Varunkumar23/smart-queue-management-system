package com.smartqueue.smart_queue_system.service.ServiceImplementation;

import com.smartqueue.smart_queue_system.dto.request.OrganizationRequest;
import com.smartqueue.smart_queue_system.dto.response.OrganizationResponse;
import com.smartqueue.smart_queue_system.entity.Organization;
import com.smartqueue.smart_queue_system.entity.SmartUser;
import com.smartqueue.smart_queue_system.enums.OrgStatus;
import com.smartqueue.smart_queue_system.exception.OrganizationAlreadyExistsException;
import com.smartqueue.smart_queue_system.exception.OrganizationNotFoundException;
import com.smartqueue.smart_queue_system.exception.UserNotFoundException;
import com.smartqueue.smart_queue_system.payload.ApiResponse;
import com.smartqueue.smart_queue_system.repository.OrganizationRepository;
import com.smartqueue.smart_queue_system.repository.SmartUserRepository;
import com.smartqueue.smart_queue_system.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImplementation implements OrganizationService {

    private final SmartUserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse<OrganizationResponse> createOrganization(OrganizationRequest request, String ownerEmail) {
        if (organizationRepository.existsByEmail(request.getEmail())) {
            throw new OrganizationAlreadyExistsException("Organization already exists with this email");
        }

        SmartUser owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UserNotFoundException("Owner not found"));

        Organization organization=modelMapper.map(request,Organization.class);
        organization.setOwner(owner);
        organization.setStatus(OrgStatus.ACTIVE);
        Organization saved = organizationRepository.save(organization);

        OrganizationResponse response=modelMapper.map(saved,OrganizationResponse.class);
        response.setOwnerName(organization.getOwner().getName());

        return ApiResponse.<OrganizationResponse>builder()
                .success(true)
                .message("Organization created successfully")
                .data(response)
                .build();

    }

    @Override
    public ApiResponse<OrganizationResponse> getOrganizationById(Long id) {
        Organization organization=organizationRepository.findById(id).orElseThrow(()->new OrganizationNotFoundException("Organization not found"));
        OrganizationResponse response=modelMapper.map(organization,OrganizationResponse.class);

        return ApiResponse.<OrganizationResponse>builder()
                .success(true)
                .message("Organization fetched successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<List<OrganizationResponse>> getAllOrganizations() {
        List<OrganizationResponse> organizations=organizationRepository.findAll().stream().map(organization -> modelMapper.map(organization,OrganizationResponse.class)).toList();
        return ApiResponse.<List<OrganizationResponse>>builder()
                .success(true)
                .message("All Organizations fetched successfully")
                .data(organizations)
                .build();
    }

    @Override
    public ApiResponse<OrganizationResponse> updateOrganization(Long id, OrganizationRequest request, String ownerEmail) {
        return null;
    }

    @Override
    public ApiResponse<OrganizationResponse> updateStatus(Long id, OrgStatus status) {
        return null;
    }
}
