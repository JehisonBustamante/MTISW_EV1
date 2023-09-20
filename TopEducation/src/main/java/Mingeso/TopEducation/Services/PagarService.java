package Mingeso.TopEducation.Services;

import Mingeso.TopEducation.Repositories.PagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagarService {
    @Autowired
    PagarRepository pagarRepository;
}
