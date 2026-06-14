package com.smartqueue.smart_queue_system.service.ServiceImplementation;

import com.smartqueue.smart_queue_system.dto.request.OrganizationRequest;
import com.smartqueue.smart_queue_system.dto.response.OrganizationResponse;
import com.smartqueue.smart_queue_system.entity.Organization;
import com.smartqueue.smart_queue_system.entity.SmartUser;
import com.smartqueue.smart_queue_system.enums.OrgStatus;
import com.smartqueue.smart_queue_system.exception.OrganizationAlreadyExistsException;
import com.smartqueue.smart_queue_system.exception.OrganizationNotFoundException;
import com.smartqueue.smart_queue_system.exception.UnauthorizedAccessException;
import com.smartqueue.smart_queue_system.exception.UserNotFoundException;
import com.smartqueue.smart_queue_system.payload.ApiResponse;
import com.smartqueue.smart_queue_system.repository.OrganizationRepository;
import com.smartqueue.smart_queue_system.repository.SmartUserRepository;
import com.smartqueue.smart_queue_system.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
        organization.setStatus(OrgStatus.PENDING);
        Organization saved = organizationRepository.save(organization);

        OrganizationResponse response=modelMapper.map(saved,OrganizationResponse.class);
        response.setOwnerName(organization.getOwner().getName());

        return ApiResponse.<OrganizationResponse>builder()
                .success(true)
                .message("Organization submitted for verification")
                .data(response)
                .build();

    }

    @Override
    public ApiResponse<OrganizationResponse> getOrganizationById(Long id) {
        Organization organization=organizationRepository.findById(id).orElseThrow(()->new OrganizationNotFoundException("Organization not found"));
        OrganizationResponse response=modelMapper.map(organization,OrganizationResponse.class);
        response.setOwnerName(organization.getOwner().getName());


        return ApiResponse.<OrganizationResponse>builder()
                .success(true)
                .message("Organization fetched successfully")
                .data(response)
                .build();


     }

    @Override
    public ApiResponse<List<OrganizationResponse>> getAllOrganizations() {

        List<OrganizationResponse> organizations = organizationRepository.findByStatus(OrgStatus.PENDING)
                .stream()
                .map(organization -> {
                    OrganizationResponse response = modelMapper.map(organization, OrganizationResponse.class);
                    response.setOwnerName(organization.getOwner().getName());
                    return response;
                })
                .toList();

        return ApiResponse.<List<OrganizationResponse>>builder()
                .success(true)
                .message("All Organizations fetched successfully")
                .data(organizations)
                .build();
    }

    @Override
    public ApiResponse<OrganizationResponse> updateOrganization(Long id, OrganizationRequest request, String ownerEmail) {
        Organization organization=organizationRepository.findById(id).orElseThrow(()->new OrganizationNotFoundException("Organization not found"));
        if (!organization.getOwner().getEmail().equals(ownerEmail)) {
            throw new UnauthorizedAccessException("You are not authorized to update this organization");
        }

        if(request.getName()!=null){
            organization.setName(request.getName());

        }
        if(request.getType()!=null){
            organization.setType(request.getType());

        }
        if(request.getEmail()!=null &&
                !request.getEmail().equals(organization.getEmail()) &&
                organizationRepository.existsByEmail(request.getEmail())) {

            throw new OrganizationAlreadyExistsException(
                    "Organization already exists with this email");
        }
        if(request.getPhone()!=null){
            organization.setPhone(request.getPhone());

        }
        if(request.getAddress()!=null){
            organization.setAddress(request.getAddress());

        }
        if(request.getCity()!=null){
            organization.setCity(request.getCity());

        }
        if(request.getState()!=null){
            organization.setState(request.getState());
        }

        Organization updated = organizationRepository.save(organization);

        OrganizationResponse response = modelMapper.map(updated, OrganizationResponse.class);
        response.setOwnerName(updated.getOwner().getName());
        return ApiResponse.<OrganizationResponse>builder().success(true).message("Organization updated successfully")
                .data(response).build();

    }


    @Override
    public ApiResponse<String> deleteOrganization(Long id, String requestorEmail) {
        Organization organization=organizationRepository.findById(id).orElseThrow(()->new OrganizationNotFoundException("Organization not found"));
        if (!organization.getOwner().getEmail().equals(requestorEmail)) {
            throw new UnauthorizedAccessException("You are not authorized to delete this organization");
        }
        organization.setStatus(OrgStatus.INACTIVE);
        organizationRepository.save(organization);
        return ApiResponse.<String>builder().success(true).message("Organization deleted successfully").data(null).build();

    }
}
