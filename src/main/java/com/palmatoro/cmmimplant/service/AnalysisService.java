package com.palmatoro.cmmimplant.service;

import java.util.Date;

import com.palmatoro.cmmimplant.domain.Analysis;
import com.palmatoro.cmmimplant.domain.Analysis.AnalysisStatus;
import com.palmatoro.cmmimplant.domain.Analysis.AnalysisType;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.AnalysisRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnalysisService {

    private AnalysisRepository analysisRepository;

    public AnalysisService(AnalysisRepository analysisRepository){
        this.analysisRepository = analysisRepository;
    }

    public Iterable<Analysis> getAllAnalysiss(){
        return analysisRepository.findAll();
    }

    public Analysis getAnalysisById(Integer id){
        Analysis analysis = analysisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No analysis found on ID: " + id));
        return analysis;
    }

    @Transactional
    public Analysis addNewAnalysis(Analysis analysis){
        return analysisRepository.save(analysis);
    }

    @Transactional
    public Analysis editAnalysis(Integer id, String identifier, AnalysisType type, String analysisIdentifier, AnalysisStatus status, String direction, Date date, Date evaluationDate, String observations){
        Analysis analysis = analysisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No analysis found on ID: " + id));

        analysis.setIdentifier(identifier);
        analysis.setType(type);
        analysis.setAnalysisIdentifier(analysisIdentifier);
        analysis.setStatus(status);
        analysis.setDirection(direction);
        analysis.setDate(date);
        analysis.setEvaluationDate(evaluationDate);
        analysis.setObservations(observations);

        return analysisRepository.save(analysis);
    }

    @Transactional
    public void deleteAnalysisById(Integer id){
        Analysis analysis = analysisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No analysis found on ID: " + id));
        analysisRepository.delete(analysis);
    }
    
}
