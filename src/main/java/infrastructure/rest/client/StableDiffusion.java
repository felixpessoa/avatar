package infrastructure.rest.client;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey = "stable-diffusion-api")
public interface StableDiffusion {

    @POST
    @Path("/sdapi/v1/img2img")
    Uni<Response> img2img(Request request);

    record Request(@JsonProperty("init_images") List<String> initImages,
            String prompt,
            @JsonProperty("negative-prompt") String negativePrompt,
            @JsonProperty("sampler-index") String samplerIndex,
            @JsonProperty("sampler-name") String samplerName,
            Integer seed,
            @JsonProperty("denoising-strenght") Double denoisingStrenght,
            @JsonProperty("cfg-scale") Double cfgScale,
            Integer steps,
            Integer width,
            Integer height,
            @JsonProperty("seed_resize_from_w") Integer seedResizeFromW,
            @JsonProperty("seed_resize_from_h") Integer seedResizeFromH,
            @JsonProperty("alwayson_script") Script alwaysonScripts) {

        public Request(String initImage) {
            this(List.of(initImage),
                    "morden disney style",
                    "ugly,disfigured",
                    "Euler a",
                    "Euler a",
                    -1,
                    0.9,
                    7.0,
                    20,
                    512,
                    512,
                    0,
                    0,
                    new Script(
                            new Script.ControlNet(List.of(
                                    new Script.ControlNet.Arg(initImage, "lineart_realistc",
                                            "control_v11p_sd15_lineart [43d4be0d]",
                                            "ControlNet is more important",
                                            true)))));
        }

        public record Script(ControlNet controlNet) {
            public record ControlNet(List<Arg> args) {
                public record Arg(@JsonProperty("input_image") String inputImage,
                        String module,
                        String model,
                        @JsonProperty("control_mode") String controlMode,
                        Boolean enabled) {

                }
            }
        }
    }

    public record Response(List<String> images) {
    }

}