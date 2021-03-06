package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/speakers")
public class SpeakersController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        return speakerRepository.getOne(id);
    }

    @PostMapping
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        //Also needs to check for children records before deleting
        speakerRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker){
        //because this is a PUT, we expect all attributes to be passed in. A PATCH would only need what
        //TODO: Add validation that all attributes passed in, otherwise return a 400 bad payload
        Speaker existing_speaker = speakerRepository.getOne(id);
        BeanUtils.copyProperties(speaker, existing_speaker, "speaker_id");
        return speakerRepository.saveAndFlush(existing_speaker);
    }

    @PatchMapping
    public Speaker patch(@PathVariable Long id, @RequestBody String speaker_bio){
        Speaker existing_speaker = speakerRepository.getOne(id);
        existing_speaker.setSpeaker_bio(speaker_bio);

        return speakerRepository.saveAndFlush(existing_speaker);
    }
}
